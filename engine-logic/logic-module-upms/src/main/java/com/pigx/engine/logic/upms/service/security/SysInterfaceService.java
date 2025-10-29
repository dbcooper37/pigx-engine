package com.pigx.engine.logic.upms.service.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.converter.RequestMappingToSysInterfaceConverter;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import com.pigx.engine.logic.upms.entity.security.SysInterface;
import com.pigx.engine.logic.upms.repository.security.SysInterfaceRepository;
import com.pigx.engine.message.core.domain.RestMapping;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SysInterfaceService extends AbstractJpaService<SysInterface, String> {

    private final SysInterfaceRepository sysInterfaceRepository;
    private final Converter<RestMapping, SysInterface> toSysInterface;

    public SysInterfaceService(SysInterfaceRepository sysInterfaceRepository) {
        this.sysInterfaceRepository = sysInterfaceRepository;
        this.toSysInterface = new RequestMappingToSysInterfaceConverter();
    }

    @Override
    public BaseJpaRepository<SysInterface, String> getRepository() {
        return sysInterfaceRepository;
    }

    /**
     * 查找SysSecurityAttribute中不存在的SysAuthority
     *
     * @return SysAuthority列表
     */
    public List<SysInterface> findAllocatable() {

        // exist sql 结构示例： SELECT * FROM article WHERE EXISTS (SELECT * FROM user WHERE article.uid = user.uid)
        Specification<SysInterface> specification = (root, criteriaQuery, criteriaBuilder) -> {

            // 构造Not Exist子查询
            Subquery<SysAttribute> subQuery = criteriaQuery.subquery(SysAttribute.class);
            Root<SysAttribute> subRoot = subQuery.from(SysAttribute.class);

            // 构造Not Exist 子查询的where条件
            Predicate subPredicate = criteriaBuilder.equal(subRoot.get("attributeId"), root.get("interfaceId"));
            subQuery.where(subPredicate);

            // 构造完整的子查询语句
            //这句话不加会报错，因为他不知道你子查询要查出什么字段。就是上面示例中的子查询中的“select *”的作用
            subQuery.select(subRoot.get("attributeId"));

            // 构造完整SQL
            // 正确的结构参考：SELECT * FROM sys_authority sa WHERE NOT EXISTS ( SELECT * FROM sys_metadata sm WHERE sm.metadata_id = sa.authority_id )
            criteriaQuery.where(criteriaBuilder.not(criteriaBuilder.exists(subQuery)));
            return criteriaQuery.getRestriction();
        };

        return this.findAll(specification);
    }

    public List<SysInterface> storeRequestMappings(Collection<RestMapping> restMappings) {
        List<SysInterface> sysAuthorities = toSysInterfaces(restMappings);
        return saveAllAndFlush(sysAuthorities);
    }

    private List<SysInterface> toSysInterfaces(Collection<RestMapping> restMappings) {
        if (CollectionUtils.isNotEmpty(restMappings)) {
            return restMappings.stream().map(toSysInterface::convert).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
