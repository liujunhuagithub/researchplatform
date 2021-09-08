package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.People;
import cn.edu.ncepu.researchplatform.entity.dto.PeopleDto;
import cn.edu.ncepu.researchplatform.entity.vo.PeopleVo;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@CacheConfig(cacheNames = "people")
public class PeopleService {
    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private EvaluateService evaluateService;

    @Cacheable(key = "'jwt'+#username")
    public PeopleDetails findByUsername(String username) {
        return peopleMapper.findByUsername(username);
    }

    public boolean updateAuthById(Integer id, Integer auth) {
        return peopleMapper.updateAuth(id, auth);
    }

    public String findIcon(String username) {
        return peopleMapper.findIcon(username);
    }
    public boolean updateIcon(String username,String icon) {
        return peopleMapper.updateIcon(username,icon);
    }
    @Cacheable(key = "#username")
    public People findAllInfo(String username) {

        People people = peopleMapper.findALLInfo(username);
        people.setAreas(areaMapper.findPeopleAreas(people.getId()));
        return people;
    }

    @Async
    public void costForEvaluate(Integer evaluateId, Integer value) {
//        People author = evaluateService.getAuthor(evaluateId);
//        peopleMapper.cost(author.getId(), value);
    }

    public boolean updatePass(String phone, String pass) {
        return peopleMapper.updatePass(phone, pass);
    }

    public Integer insertPeople(People people) {
        return peopleMapper.insertPeople(people);
    }

    public boolean updateInfo(People people) {
        return peopleMapper.updateInfo(people);
    }

    public boolean updateReal(String realname, String idCard, String username) {
        return peopleMapper.updateReal(realname, idCard, username);
    }

    public boolean updatePhone(String phone, String username) {
        return peopleMapper.updatePhone(phone, username);
    }

    public People findById(Integer id) {
        return peopleMapper.findById(id);
    }

    public PeopleVo findByCondition(PeopleDto dto) {
        List<People> peopleVos = peopleMapper.findByCondition(dto);
        Integer total = peopleMapper.findCountByCondition(dto);
        return new PeopleVo(total,dto.getCurrent(),dto.getSize(),peopleVos);
    }
}
