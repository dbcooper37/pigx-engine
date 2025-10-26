package com.pigx.engine.logic.upms.converter;

import com.pigx.engine.core.definition.utils.WellFormedUtils;
import com.pigx.engine.logic.upms.entity.hr.SysOrganization;
import cn.hutool.v7.core.tree.TreeNode;
import org.springframework.core.convert.converter.Converter;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/converter/SysOrganizationToTreeNodeConverter.class */
public class SysOrganizationToTreeNodeConverter implements Converter<SysOrganization, TreeNode<String>> {
    public TreeNode<String> convert(SysOrganization sysOrganization) {
        TreeNode<String> treeNode = new TreeNode<>();
        treeNode.setId(sysOrganization.getOrganizationId());
        treeNode.setName(sysOrganization.getOrganizationName());
        treeNode.setParentId(WellFormedUtils.parentId(sysOrganization.getParentId()));
        return treeNode;
    }
}
