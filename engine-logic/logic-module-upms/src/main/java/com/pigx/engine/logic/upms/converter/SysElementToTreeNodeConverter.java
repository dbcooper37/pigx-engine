package com.pigx.engine.logic.upms.converter;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.view.vue.BaseMeta;
import com.pigx.engine.core.definition.domain.view.vue.ChildMeta;
import com.pigx.engine.core.definition.domain.view.vue.ParentMeta;
import com.pigx.engine.core.definition.domain.view.vue.RootMeta;
import com.pigx.engine.core.definition.utils.WellFormedUtils;
import com.pigx.engine.logic.upms.entity.security.SysElement;
import com.pigx.engine.logic.upms.entity.security.SysRole;
import cn.hutool.v7.core.tree.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/converter/SysElementToTreeNodeConverter.class */
public class SysElementToTreeNodeConverter implements Converter<SysElement, TreeNode<String>> {
    public TreeNode<String> convert(SysElement sysMenu) {
        TreeNode<String> treeNode = new TreeNode<>();
        treeNode.setId(sysMenu.getElementId());
        treeNode.setName(sysMenu.getPath());
        treeNode.setWeight(sysMenu.getRanking());
        treeNode.setParentId(WellFormedUtils.parentId(sysMenu.getParentId()));
        treeNode.setExtra(getExtra(sysMenu));
        return treeNode;
    }

    private Map<String, Object> getExtra(SysElement sysMenu) {
        Map<String, Object> extra = new HashMap<>();
        if (StringUtils.isBlank(sysMenu.getParentId())) {
            RootMeta meta = new RootMeta();
            meta.setSort(sysMenu.getRanking());
            setBaseMeta(sysMenu, meta);
            extra.put("meta", meta);
            extra.put("redirect", sysMenu.getRedirect());
        } else if (BooleanUtils.isTrue(sysMenu.getHaveChild())) {
            ParentMeta meta2 = new ParentMeta();
            meta2.setHideAllChild(sysMenu.getHideAllChild());
            setBaseMeta(sysMenu, meta2);
            extra.put("meta", meta2);
            extra.put("componentName", sysMenu.getName());
        } else {
            ChildMeta meta3 = new ChildMeta();
            meta3.setDetailContent(sysMenu.getDetailContent());
            setBaseMeta(sysMenu, meta3);
            extra.put("meta", meta3);
            extra.put("componentName", sysMenu.getName());
        }
        extra.put("componentPath", sysMenu.getComponent());
        Set<SysRole> sysRoles = sysMenu.getRoles();
        if (CollectionUtils.isNotEmpty(sysRoles)) {
            List<String> roles = (List) sysRoles.stream().map((v0) -> {
                return v0.getRoleCode();
            }).collect(Collectors.toList());
            extra.put(SystemConstants.ROLES, roles);
        } else {
            extra.put(SystemConstants.ROLES, new ArrayList());
        }
        return extra;
    }

    private void setBaseMeta(SysElement sysMenu, BaseMeta meta) {
        meta.setIcon(sysMenu.getIcon());
        meta.setTitle(sysMenu.getTitle());
        meta.setIgnoreAuth(sysMenu.getIgnoreAuth());
        meta.setNotKeepAlive(sysMenu.getNotKeepAlive());
    }
}
