package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.service.AreaService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class AreaController {
    @Autowired
    private AreaService areaService;

    @GetMapping("/area")
    public Map<Integer, Area> 获取所有Area() {
        return areaService.findAllArea();
    }

    @GetMapping("/area/childOne/{parentId}")
    public List<Tree<Integer>> 获取所有Area(Integer parentId) {
        List<TreeNode<Integer>> nodeList = new LinkedList<>();
        areaService.findAllArea().values().stream().forEach(area -> nodeList.add(new TreeNode<>(area.getId(), area.getParentId(), area.getName(), 0)));
        return TreeUtil.build(nodeList, parentId);
    }

}
