package com.pigx.engine.logic.upms.converter;

import cn.hutool.v7.core.tree.TreeNode;
import com.pigx.engine.core.definition.utils.WellFormedUtils;
import com.pigx.engine.logic.upms.entity.hr.SysOrganization;
import org.springframework.core.convert.converter.Converter;


public class SysOrganizationToTreeNodeConverter implements Converter<SysOrganization, TreeNode<String>> {
    @Override
    public TreeNode<String> convert(SysOrganization sysOrganization) {
        TreeNode<String> treeNode = new TreeNode<>();
        treeNode.setId(sysOrganization.getOrganizationId());
        treeNode.setName(sysOrganization.getOrganizationName());
        treeNode.setParentId(WellFormedUtils.parentId(sysOrganization.getParentId()));
        return treeNode;
    }
}
