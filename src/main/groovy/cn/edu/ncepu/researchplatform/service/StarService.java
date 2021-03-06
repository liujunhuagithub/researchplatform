package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.entity.Star;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.mapper.StarMapper;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import cn.edu.ncepu.researchplatform.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
    @Lazy
    private PeopleService peopleService;
    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private WebSocketService wsService;
    @Autowired
    @Lazy
    private AreaService areaService;

    @Transactional(rollbackFor = Exception.class)
    public boolean saveStar(Integer evaluateId, Integer flag) {
        Integer peopleId = peopleService.findByUsername(Utils.getCurrent()).getId();
        Star star = starMapper.findOne(evaluateId, peopleId);
        if (star == null) {
            starMapper.saveStar(evaluateId, peopleId, flag);
        }
        starMapper.updateStar(evaluateId, peopleId, flag);
        if (flag == 0) flag = -1;
        return starMapper.updateEvaluateStar(evaluateId, flag);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public boolean isContainArea(Integer evaluateId) {
        Integer articleId = articleMapper.findIdaboutEvaluate(evaluateId);
        List<Area> articleAreas = areaMapper.findArticleAreas(articleId);
        List<Area> peopleAreas = areaMapper.findPeopleAreas(peopleService.findByUsername(Utils.getCurrent()).getId());
        return areaService.isAreaContain(peopleAreas, articleAreas);
    }

    @Async
    public void notifyAuthorStar(Integer evaluateId) {
        wsService.notifyAuthorStar(peopleMapper.findAuthorByArticleId(articleMapper.findIdaboutEvaluate(evaluateId)).getUsername());
    }
}
