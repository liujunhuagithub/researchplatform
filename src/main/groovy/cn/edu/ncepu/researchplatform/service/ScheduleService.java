package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Article;
import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import cn.edu.ncepu.researchplatform.mapper.EvaluateMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.mapper.StarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class ScheduleService {
    @Autowired
    private RankService rankService;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StarMapper starMapper;
    @Autowired
    private EvaluateMapper evaluateMapper;
    @Autowired
    private PeopleMapper peopleMapper;

    @Scheduled(cron = "@daily")
    public void deleteInvalidStar() {
        starMapper.deleteInvalidStar();
    }

    @Scheduled(cron = "@monthly")
    public void updatePeopleWeight() {
        peopleMapper.updateWeight();
    }

    @Scheduled(cron = "@monthly")
    public void updateArticleWeight() {
        articleMapper.updateWeight();
    }

    @Scheduled(cron = "@daily")
    public void updateArticleScore() {
        while (true) {
            Article needCalculateArticle = articleMapper.calculateArticle();
            if (needCalculateArticle == null) {
                break;
            }
            articleMapper.updateScore(needCalculateArticle.getId());
            evaluateMapper.updateArticleCalculateByArticleId(needCalculateArticle.getId(), 0);
        }
    }

    @Scheduled(cron = "@weekly")
    public void deleteArticleAboutEvaluate() {
        evaluateMapper.deleteArticleAboutEvaluate();
    }

    @Scheduled(cron = "@weekly")
    public void deleteBlackPeople() {
        peopleMapper.deleteBlackPeople();
    }

    @Scheduled(cron = "@weekly")
    public void deleteIllegeEvaluate() {
        evaluateMapper.deleteIllegeEvaluate();
    }
}
