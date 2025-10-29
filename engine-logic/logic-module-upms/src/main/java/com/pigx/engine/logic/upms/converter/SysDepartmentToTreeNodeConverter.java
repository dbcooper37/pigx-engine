package com.pigx.engine.logic.upms.converter;

import cn.hutool.v7.core.tree.TreeNode;
import com.pigx.engine.core.definition.utils.WellFormedUtils;
import com.pigx.engine.logic.upms.entity.hr.SysDepartment;
import org.springframework.core.convert.converter.Converter;


public class SysDepartmentToTreeNodeConverter implements Converter<SysDepartment, TreeNode<String>> {
    @Override
    public TreeNode<String> convert(SysDepartment sysDepartment) {
        TreeNode<String> treeNode = new TreeNode<>();
        treeNode.setId(sysDepartment.getDepartmentId());
        treeNode.setName(sysDepartment.getDepartmentName());
        treeNode.setParentId(WellFormedUtils.parentId(sysDepartment.getParentId()));
        return treeNode;
    }
}
