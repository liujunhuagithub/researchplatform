package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @CacheEvict(cacheManager = "rankCacheManager", value = "area", key = "'areas'")
    public boolean updateDisabled(Integer disabled, Integer id) {
        return areaMapper.updateDisabled(disabled, id);
    }

    public boolean isAreaContain(List<Area> peopleAreas, List<Area> targetAreas) {
        Set<Integer> ps = peopleAreas.stream().map(Area::getParentId).collect(Collectors.toSet());
        Set<Integer> ts = targetAreas.stream().map(Area::getParentId).collect(Collectors.toSet());

        ps.retainAll(ts);
        if (ps.size() == 0) {
            throw CustomException.AREA_ERROR_Exception;
        }
        return true;
    }

    public Tree<Integer> findTree() {
        List<TreeNode<Integer>> nodeList = new LinkedList<>();
        areaMapper.findAllArea().forEach(area -> nodeList.add(new TreeNode<>(area.getId(), area.getParentId(), area.getName(), 0)));
        return TreeUtil.buildSingle(nodeList);
    }
}
