package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.entity.Star;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.mapper.StarMapper;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import cn.edu.ncepu.researchplatform.utils.Utils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StarService {
    @Autowired
    private StarMapper starMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private WebSocketService wsService;

    @Transactional(rollbackFor = Exception.class)
    public boolean saveStar(Integer evaluateId, Integer flag) {
        Integer peopleId = peopleService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        Star star = starMapper.findOne(evaluateId, peopleId);
        if (star == null) {
            return starMapper.saveStar(evaluateId, peopleId, flag);
        }
        return starMapper.updateStar(evaluateId, peopleId, flag);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public boolean isEqualArea(Integer evaluateId) {
        Integer article_id = articleMapper.findIdaboutEvaluate(evaluateId);
        List<Area> articleAreas = areaMapper.findArticleAreas(article_id);
        PeopleDetails peopleDetails = peopleService.findByUsername(Utils.getCurrent());
        List<Area> peopleAreas = areaMapper.findPeopleAreas(peopleDetails.getId());

        articleAreas.retainAll(peopleAreas);
        if (articleAreas.size() == 0) {
            return false;
        }
        return true;
    }

    @Async
    public void notifyAuthorStar(Integer evaluateId) {
        wsService.notifyAuthorStar(peopleMapper.findAuthorById(articleMapper.findIdaboutEvaluate(evaluateId)).getUsername());
    }
}
