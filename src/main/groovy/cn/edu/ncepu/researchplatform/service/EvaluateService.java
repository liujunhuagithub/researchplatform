package cn.edu.ncepu.researchplatform.service;


import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.entity.Evaluate;
import cn.edu.ncepu.researchplatform.entity.People;
import cn.edu.ncepu.researchplatform.entity.Summary;
import cn.edu.ncepu.researchplatform.entity.vo.EvaluatePageVo;
import cn.edu.ncepu.researchplatform.mapper.*;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import cn.edu.ncepu.researchplatform.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

@Service
@CacheConfig(cacheNames = "evaluate")
public class EvaluateService {
    @Autowired
    private EvaluateMapper evaluateMapper;
    @Autowired
    @Lazy
    private PeopleService peopleService;
    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private SummaryMapper summaryMapper;
    @Autowired
    @Lazy
    private AreaService areaService;

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Integer id, Integer peopleId) {
        evaluateMapper.updateArticleCalculateByEvaluateId(id, 1);
        return evaluateMapper.deleteByIdAndPeopleId(id, peopleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateFlag(Integer id, Integer flag) {
        evaluateMapper.updateArticleCalculateByEvaluateId(id, 1);
        evaluateMapper.deleteById(id);
        return evaluateMapper.updateFlag(id, flag);
    }


    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Cacheable(key = "'author'+#evaluateId")
    public People getAuthor(Integer evaluateId) {
        Integer articleId = articleMapper.findIdaboutEvaluate(evaluateId);
        return peopleMapper.findAuthorByArticleId(articleId);
    }

    public List<Integer> findBypeopleToArticle(Integer articleId, String username) {
        return evaluateMapper.findBypeopleToArticle(articleId, peopleService.findByUsername(username).getId());
    }

    @Cacheable(key = "'findByArticleId'+#articleId+','+#current+','+#size")
    public List<Evaluate> findByArticleId(Integer articleId, Integer current, Integer size) {
        People author = peopleMapper.findAuthorByArticleId(articleId);
        PeopleDetails currentUser = peopleService.findByUsername(Utils.getCurrent());
        Integer currentId = currentUser == null ? null : currentUser.getId();
        return evaluateMapper.findEvaluateByArticleId(articleId, currentId != null && currentId.equals(author.getId()), current, size);
    }

    public Integer insertDiscuss(Evaluate evaluate) {
//        evaluateMapper.updateArticleCalculateByArticleId(evaluate.getArticleId(), 1);
        return evaluateMapper.insertDiscuss(evaluate);
    }

    @Cacheable(key = "'findDisscussByParentId'+#articleId+','+#current+','+#size")
    public List<Evaluate> findDisscussByParentId(Integer parentId, Integer current, Integer size) {
        return evaluateMapper.findDisscussByParentId(parentId, current, size);
    }

    public boolean insertBatchEvaluate(Integer peopleId, TreeMap<Integer, Evaluate> evaluates) {
        evaluates.forEach((integer, evaluate) -> {
            if (evaluate.getNiming()==null){
                evaluate.setNiming(false);
            }
            if (evaluate.getParentId()==null){
                evaluate.setParentId(0);
            }
            evaluate.setPeopleId(peopleId);
            evaluateMapper.insertEvaluate(evaluate);
            evaluateMapper.updateArticleCalculateByArticleId(evaluate.getArticleId(), 1);
        });

        return true;
    }

    public Evaluate findById(Integer id) {
        return evaluateMapper.findById(id);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public boolean isArticleContainArea(Integer articleId) {
        List<Area> articleAreas = areaMapper.findArticleAreas(articleId);
        List<Area> peopleAreas = areaMapper.findPeopleAreas(peopleService.findByUsername(Utils.getCurrent()).getId());
        return areaService.isAreaContain(peopleAreas, articleAreas);
    }

    public EvaluatePageVo findByPeopleId(Integer peopleId, Integer current, Integer size) {
        List<Evaluate> evaluates = evaluateMapper.findByPeopleId(peopleId, current, size);
        Integer total = evaluateMapper.findCountByPeopleId(peopleId);
        return new EvaluatePageVo(total, current, size, evaluates);
    }
}
