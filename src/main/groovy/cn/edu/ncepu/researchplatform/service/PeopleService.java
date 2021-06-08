package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import cn.hutool.core.util.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "people")
public class PeopleService {
    @Autowired
    private PeopleMapper peopleMapper;

    @Cacheable(key = "#username")
    public PeopleDetails findByUsername(String username) {
        return peopleMapper.findByUsername(Integer.parseInt(username));
    }
}
