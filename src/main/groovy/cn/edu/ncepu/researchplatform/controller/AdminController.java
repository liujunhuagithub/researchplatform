package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.common.R;
import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AreaService areaService;

    @PostMapping("/area")
    public boolean 新增Area(@RequestBody Area area) {
        areaService.insertArea(area);
        return true;
    }
}
