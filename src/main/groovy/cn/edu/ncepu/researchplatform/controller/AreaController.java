package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
