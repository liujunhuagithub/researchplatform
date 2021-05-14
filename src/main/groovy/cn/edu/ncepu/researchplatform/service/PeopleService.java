package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import cn.hutool.core.util.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {
    @Autowired
    private PeopleMapper peopleMapper;

    public PeopleDetails findByUsername(String username) {
        return peopleMapper.findByUsername(Integer.parseInt(username));
    }
}
