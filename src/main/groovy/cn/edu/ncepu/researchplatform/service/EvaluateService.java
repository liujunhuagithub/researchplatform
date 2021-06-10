package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Article;
import cn.edu.ncepu.researchplatform.entity.Evaluate;
import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import cn.edu.ncepu.researchplatform.mapper.EvaluateMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;


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

    public boolean isIllegalEvaluate(String content) {
        //审核Evaluate，暂时95%通过率
        return Math.random() > 0.05;
    }

    public boolean idAuthorAdpot(Integer evaluateId) {
        Integer article_id = articleMapper.findIdaboutEvaluate(evaluateId);
        return peopleMapper.findAuthorById(article_id).getUsername().equals(Utils.getCurrent());
    }
}
