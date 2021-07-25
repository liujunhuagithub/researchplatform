package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AreaService {
    @Autowired
    private AreaMapper areaMapper;
    @CacheEvict(cacheManager = "rankCacheManager", value = "area", key = "'areas'")
    public void insertArea(Area area) {
        Integer newId = areaMapper.insertArea(area);
    }

    @Cacheable(cacheManager = "rankCacheManager", value = "area", key = "'areas'")
    public Map<Integer, Area> findAllArea() {
        List<Area> allArea = areaMapper.findAllArea();
        return allArea.stream().collect(Collectors.toMap(Area::getId, area -> area));
    }

    @CacheEvict(cacheManager = "rankCacheManager",value = "area", allEntries = true)
    public boolean updateDisabled(Integer disabled, Integer id) {
        return areaMapper.updateDisabled(disabled, id);
    }

    public boolean isAreaContain(List<Area> peopleAreas, List<Area> targetAreas) {
        targetAreas.retainAll(peopleAreas);
        if (targetAreas.size() == 0) {
            throw CustomException.AREA_ERROR_Exception;
        }
        return true;
    }
}
