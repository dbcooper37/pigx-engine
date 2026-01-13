package com.pigx.engine.data.tenant.crypto;

import cn.hutool.v7.core.codec.binary.Base64;
import cn.hutool.v7.core.util.ByteUtil;
import cn.hutool.v7.crypto.SecureUtil;
import cn.hutool.v7.crypto.symmetric.AES;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * JPA AttributeConverter for encrypting/decrypting sensitive string data.
 * <p>
 * This converter automatically encrypts data before persisting to the database
 * and decrypts it when reading from the database, providing transparent
 * encryption at rest for sensitive fields like passwords.
 * </p>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 * {@code @Convert(converter = EncryptedStringConverter.class)}
 * private String password;
 * </pre>
 *
 * <p><b>Security Note:</b> The encryption key should be configured via
 * environment variables or secure vault in production environments.</p>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
@Converter
public class EncryptedStringConverter implements AttributeConverter<String, String> {

    private static final Logger log = LoggerFactory.getLogger(EncryptedStringConverter.class);

    /**
     * Encryption key for AES algorithm.
     * In production, this should be loaded from secure configuration.
     * Key must be exactly 16 characters for AES-128.
     */
    private static final String ENCRYPTION_KEY = getEncryptionKey();

    private static final String ENCRYPTED_PREFIX = "ENC:";

    private static String getEncryptionKey() {
        // Try to get key from environment variable first
        String envKey = System.getenv("PIGX_TENANT_ENCRYPTION_KEY");
        if (StringUtils.isNotBlank(envKey) && envKey.length() == 16) {
            return envKey;
        }
        // Try system property
        String propKey = System.getProperty("pigx.tenant.encryption.key");
        if (StringUtils.isNotBlank(propKey) && propKey.length() == 16) {
            return propKey;
        }
        // Fallback to default (should be changed in production!)
        log.warn("[PIGXD] |- Using default encryption key for tenant passwords. " +
                "Set PIGX_TENANT_ENCRYPTION_KEY environment variable in production!");
        return "PigXEngine16Key!";
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (StringUtils.isBlank(attribute)) {
            return attribute;
        }

        // Don't double-encrypt
        if (attribute.startsWith(ENCRYPTED_PREFIX)) {
            return attribute;
        }

        try {
            AES aes = SecureUtil.aes(ByteUtil.toUtf8Bytes(ENCRYPTION_KEY));
            byte[] encrypted = aes.encrypt(ByteUtil.toUtf8Bytes(attribute));
            String encodedValue = Base64.encode(encrypted);
            log.trace("[PIGXD] |- Encrypted sensitive data for database storage");
            return ENCRYPTED_PREFIX + encodedValue;
        } catch (Exception e) {
            log.error("[PIGXD] |- Failed to encrypt sensitive data: {}", e.getMessage());
            throw new RuntimeException("Failed to encrypt sensitive data", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData)) {
            return dbData;
        }

        // Check if data is encrypted
        if (!dbData.startsWith(ENCRYPTED_PREFIX)) {
            // Data is not encrypted (legacy data), return as-is
            // Log warning for migration purposes
            log.warn("[PIGXD] |- Found unencrypted sensitive data in database. " +
                    "Consider running migration to encrypt existing data.");
            return dbData;
        }

        try {
            String encryptedValue = dbData.substring(ENCRYPTED_PREFIX.length());
            AES aes = SecureUtil.aes(ByteUtil.toUtf8Bytes(ENCRYPTION_KEY));
            byte[] decrypted = aes.decrypt(Base64.decode(encryptedValue));
            log.trace("[PIGXD] |- Decrypted sensitive data from database");
            return new String(decrypted);
        } catch (Exception e) {
            log.error("[PIGXD] |- Failed to decrypt sensitive data: {}", e.getMessage());
            throw new RuntimeException("Failed to decrypt sensitive data", e);
        }
    }
}
