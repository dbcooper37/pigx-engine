package com.pigx.engine.oss.dialect.minio.converter;

import com.pigx.engine.oss.dialect.minio.domain.GroupDomain;
import io.minio.admin.GroupInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;


public class GroupInfoToDomainConverter implements Converter<GroupInfo, GroupDomain> {
    @Override
    public GroupDomain convert(GroupInfo groupInfo) {

        GroupDomain domain = new GroupDomain();

        if (ObjectUtils.isNotEmpty(groupInfo)) {
            domain.setName(groupInfo.name());
            domain.setStatus(groupInfo.status());
            domain.setMembers(groupInfo.members());
            domain.setPolicy(groupInfo.policy());
        }

        return domain;
    }
}
