package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.People;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import cn.hutool.core.util.PhoneUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "people")
public class PeopleService {
    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private AreaMapper areaMapper;

    @Cacheable(key = "'jwt'+#username")
    public PeopleDetails findByUsername(String username) {
        return peopleMapper.findByUsername(Integer.parseInt(username));
    }

    public boolean updateAuthById(Integer id, Integer auth) {
        return peopleMapper.updateAuth(id, auth);
    }

    public String findIcon(Integer id) {
        return peopleMapper.findIcon(id);
    }

    @Cacheable(key = "#id")
    public People findAllInfo(Integer id) {
        People people = peopleMapper.findALLInfo(id);
        people.setAreas(areaMapper.findPeopleAreas(people.getId()));
        return people;
    }
}
