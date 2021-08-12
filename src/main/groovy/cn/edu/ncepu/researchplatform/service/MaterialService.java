package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Material;
import cn.edu.ncepu.researchplatform.entity.dto.MaterialDto;
import cn.edu.ncepu.researchplatform.entity.vo.MaterialPageVo;
import cn.edu.ncepu.researchplatform.mapper.MaterialMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MaterialService {
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private ObjectMapper om;
    @Value("${customize.save-location}")
    private String pathPre;

    public Integer insertMaterial(Material material) {
        return materialMapper.insertMaterial(material);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean throughBatchArea(Integer materialId, Integer flag) throws JsonProcessingException {
        Material material = materialMapper.findById(materialId);
        if (flag.equals(1)) {
            for (Integer areaId : om.readValue(material.getAreaTemp(), Integer[].class)) {
                materialMapper.throughArea(material.getPeopleId(), areaId);
            }
        }

        materialMapper.updateFlag(materialId, flag);
        if ("guest".equals(peopleMapper.findById(material.getPeopleId()).getAuth())) {
            peopleMapper.updateAuth(material.getPeopleId(), 1);
        }
        return true;
    }

    public MaterialPageVo findByPage(MaterialDto dto) {
        MaterialPageVo vo = new MaterialPageVo();
        vo.setCurrent(dto.getCurrent());
        vo.setSize(dto.getSize());
        vo.setMaterials(materialMapper.findByCondition(dto));
        vo.setTotal(materialMapper.findCountByCondition(dto));
        return vo;
    }

    @Cacheable(value = "material", key = "#id")
    public Material findById(Integer id) {
        return materialMapper.findById(id);
    }

    public List<Material> findByPeopleId(Integer PeopleId) {
        return materialMapper.findByPeopleId(PeopleId);
    }

    public boolean deleteById(Integer id, Integer peopleId) {
        Paths.get(pathPre, "ResearchPlatformFiles", "material", materialMapper.findById(id).getPath()).toFile().delete();
        return materialMapper.deleteById(id, peopleId);
    }
}
