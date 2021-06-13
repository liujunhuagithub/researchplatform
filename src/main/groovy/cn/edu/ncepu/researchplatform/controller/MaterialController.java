package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.entity.Material;
import cn.edu.ncepu.researchplatform.entity.vo.MaterialVo;
import cn.edu.ncepu.researchplatform.service.AreaService;
import cn.edu.ncepu.researchplatform.service.MaterialService;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ObjectMapper om;

    @PostMapping("/material")
    public Integer 新增material(Material material) {
        material.setPeopleId(peopleService.findByUsername(Utils.getCurrent()).getId());
        return materialService.insertMaterial(material);
    }

    @DeleteMapping("/material/{materialId}")
    public boolean 删除material(@PathVariable Integer materialId) {
        Integer peopleId = peopleService.findByUsername(Utils.getCurrent()).getId();
        return materialService.deleteById(materialId, peopleId);
    }

    @GetMapping("/material/{materialId}")
    public MaterialVo 查询Id(@PathVariable Integer materialId) throws JsonProcessingException {
        Material material = materialService.fingById(materialId);
        List<Area> areas = Arrays.stream(om.readValue(material.getArea(), Integer[].class)).map(i -> areaService.findAllArea().get(i)).collect(Collectors.toList());
        return new MaterialVo(material, areas, peopleService.findById(material.getPeopleId()));
    }
}
