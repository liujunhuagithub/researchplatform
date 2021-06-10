package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {
    @Autowired
    private AreaMapper areaMapper;

    public void insertArea(Area area) {
        Integer newId = areaMapper.insertArea(area);
    }

    @Cacheable(cacheManager = "rankCacheManager",value = "area", key = "'areas'")
    public List<Area> findAllArea() {
        return areaMapper.findAllArea();
    }

    @CacheEvict(value = "area",allEntries = true)
    public boolean updateName(String name, Integer id) {
        return areaMapper.updateName(name, id);
    }
}
