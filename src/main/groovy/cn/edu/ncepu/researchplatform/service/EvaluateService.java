package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.mapper.EvaluateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EvaluateService {
    @Autowired
    private EvaluateMapper evaluateMapper;
@Autowired
    private PeopleService peopleService;
    @Transactional(rollbackFor = Exception.class)

    public boolean deleteByIdPeopleId(Integer id,String username) {
        return evaluateMapper.deleteByIdPeopleId(id,peopleService.findByUsername(username).getId());
    }
}
