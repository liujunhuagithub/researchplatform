package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AreaController {
    @Autowired
    private AreaService areaService;

}
