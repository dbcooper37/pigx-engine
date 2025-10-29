package com.pigx.engine.oss.dialect.minio.converter.sse;

import cn.hutool.v7.crypto.KeyUtil;
import cn.hutool.v7.crypto.symmetric.SymmetricAlgorithm;
import com.pigx.engine.oss.dialect.core.exception.OssInvalidKeyException;
import com.pigx.engine.oss.dialect.core.exception.OssNoSuchAlgorithmException;
import io.minio.ServerSideEncryptionCustomerKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class RequestToServerSideEncryptionCustomerKeyConverter implements Converter<String, ServerSideEncryptionCustomerKey> {

    private static final Logger log = LoggerFactory.getLogger(RequestToServerSideEncryptionCustomerKeyConverter.class);

    @Override
    public ServerSideEncryptionCustomerKey convert(String customerKey) {
        if (StringUtils.isNotBlank(customerKey)) {
            SecretKey secretKey = KeyUtil.generateKey(SymmetricAlgorithm.AES.getValue(), 256);

            try {
                return new ServerSideEncryptionCustomerKey(secretKey);
            } catch (InvalidKeyException e) {
                log.error("[Herodotus] |- Minio catch InvalidKeyException in ObjectReadRequest prepare.", e);
                throw new OssInvalidKeyException(e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in ObjectReadRequest prepare.", e);
                throw new OssNoSuchAlgorithmException(e.getMessage());
            }
        }

        return null;
    }
}
