package com.pigx.engine.oss.dialect.minio.converter;

import com.pigx.engine.oss.dialect.minio.domain.UserDomain;
import io.minio.admin.Status;
import io.minio.admin.UserInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;


public class UserInfoToDomainConverter implements Converter<UserInfo, UserDomain> {
    @Override
    public UserDomain convert(UserInfo userInfo) {

        UserDomain domain = new UserDomain();

        if (ObjectUtils.isNotEmpty(userInfo)) {
            domain.setSecretKey(userInfo.secretKey());
            domain.setPolicyName(userInfo.policyName());
            domain.setMemberOf(userInfo.memberOf());
            domain.setStatus(Status.fromString(userInfo.status().name()));
        }

        return domain;
    }
}
