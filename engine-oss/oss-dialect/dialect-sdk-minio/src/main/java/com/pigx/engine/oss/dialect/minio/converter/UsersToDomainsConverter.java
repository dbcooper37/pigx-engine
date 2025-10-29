package com.pigx.engine.oss.dialect.minio.converter;

import com.pigx.engine.oss.dialect.minio.domain.UserDomain;
import io.minio.admin.UserInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Collections;
import java.util.List;
import java.util.Map;


public class UsersToDomainsConverter implements Converter<Map<String, UserInfo>, List<UserDomain>> {

    private final Converter<UserInfo, UserDomain> toDomain = new UserInfoToDomainConverter();

    @Override
    public List<UserDomain> convert(Map<String, UserInfo> source) {
        if (MapUtils.isNotEmpty(source)) {
            return source.entrySet().stream().map(entry -> {
                UserDomain domain = toDomain.convert(entry.getValue());
                domain.setAccessKey(entry.getKey());
                return domain;
            }).toList();
        }

        return Collections.emptyList();
    }
}
