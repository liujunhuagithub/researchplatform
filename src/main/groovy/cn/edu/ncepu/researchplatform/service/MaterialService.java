package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Material;
import cn.edu.ncepu.researchplatform.mapper.MaterialMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialService {
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private ObjectMapper om;

    public Integer insertMaterial(Material material) {
        return materialMapper.insertMaterial(material);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean throughBatchArea(Integer materialId, Integer flag) throws JsonProcessingException {
        Material material = materialMapper.findById(materialId);
        for (Integer areaId : om.readValue(material.getArea(), Integer[].class)) {
            materialMapper.throughArea(material.getPeopleId(), areaId);
        }
        materialMapper.updateFlag(materialId, flag);
        if("guest".equals(peopleMapper.findById(material.getPeopleId()).getAuth())){
            peopleMapper.updateAuth(material.getPeopleId(),1);
        }
        return true;
    }

    public Material findById(Integer id) {
        return materialMapper.findById(id);
    }
    public List<Material> findByPeopleId(Integer PeopleId){
        return materialMapper.findByPeopleId(PeopleId);
    }

    public boolean deleteById(Integer id, Integer peopleId) {
        return materialMapper.deleteById(id, peopleId);
    }
}
