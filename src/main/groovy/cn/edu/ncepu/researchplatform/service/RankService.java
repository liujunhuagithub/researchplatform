package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Article;
import cn.edu.ncepu.researchplatform.entity.Evaluate;
import cn.edu.ncepu.researchplatform.entity.People;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import cn.edu.ncepu.researchplatform.mapper.EvaluateMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankService {
    private static final Logger logger = LoggerFactory.getLogger(RankService.class);
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private EvaluateMapper evaluateMapper;

    public List<People> peoples() {
        logger.info("生成peoples  rank");
        return peopleMapper.rankPeople();
    }

    public List<Evaluate> evaluate() {
        return evaluateMapper.rankEvaluate();
    }

    public List<Article> article() {
        return articleMapper.rankArticle();

    }

    public List<People> author() {
        return peopleMapper.rankPeople();
    }
}
