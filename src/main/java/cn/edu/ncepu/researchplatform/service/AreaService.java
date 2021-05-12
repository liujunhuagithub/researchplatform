package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaService {
    @Autowired
    private AreaMapper areaMapper;
}
