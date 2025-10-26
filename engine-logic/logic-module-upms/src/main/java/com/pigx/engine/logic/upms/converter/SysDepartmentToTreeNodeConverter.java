package com.pigx.engine.logic.upms.converter;

import com.pigx.engine.core.definition.utils.WellFormedUtils;
import com.pigx.engine.logic.upms.entity.hr.SysDepartment;
import cn.hutool.v7.core.tree.TreeNode;
import org.springframework.core.convert.converter.Converter;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/converter/SysDepartmentToTreeNodeConverter.class */
public class SysDepartmentToTreeNodeConverter implements Converter<SysDepartment, TreeNode<String>> {
    public TreeNode<String> convert(SysDepartment sysDepartment) {
        TreeNode<String> treeNode = new TreeNode<>();
        treeNode.setId(sysDepartment.getDepartmentId());
        treeNode.setName(sysDepartment.getDepartmentName());
        treeNode.setParentId(WellFormedUtils.parentId(sysDepartment.getParentId()));
        return treeNode;
    }
}
