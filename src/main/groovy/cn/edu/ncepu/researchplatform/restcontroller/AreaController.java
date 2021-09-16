package cn.edu.ncepu.researchplatform.restcontroller;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.service.AreaService;
import cn.hutool.core.lang.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/area/child/{id}")
    public List<Tree<Integer>> 获取ziArea(@PathVariable(required = true) Integer id) {
        return areaService.findTree().getNode(id).getChildren();
    }

}
