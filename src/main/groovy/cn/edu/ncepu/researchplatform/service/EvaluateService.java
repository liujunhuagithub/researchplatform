package cn.edu.ncepu.researchplatform.service;


import cn.edu.ncepu.researchplatform.entity.Evaluate;
import cn.edu.ncepu.researchplatform.entity.People;
import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import cn.edu.ncepu.researchplatform.mapper.EvaluateMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EvaluateService {
    @Autowired
    private EvaluateMapper evaluateMapper;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIdPeopleId(Integer id, String username) {
        return evaluateMapper.deleteByIdPeopleId(id, peopleService.findByUsername(username).getId());
    }


    public boolean updateFlag(Integer id, Integer flag) {
        return evaluateMapper.updateFlag(id, flag);
    }


    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public People getAuthor(Integer evaluateId) {
        Integer article_id = articleMapper.findIdaboutEvaluate(evaluateId);
        return peopleMapper.findAuthorByArticleId(article_id);
    }

    public List<Integer> findBypeopleToArticle(Integer articleId, String  username) {
        return evaluateMapper.findBypeopleToArticle(articleId, peopleService.findByUsername(username).getId());
    }

    public List<Evaluate> findByArticleId(Integer articleId){
        People author = peopleMapper.findAuthorByArticleId(articleId);
       return evaluateMapper.findByArticleId(articleId,peopleService.findByUsername(Utils.getCurrent()).getId().equals(author.getId()));
    }
}
