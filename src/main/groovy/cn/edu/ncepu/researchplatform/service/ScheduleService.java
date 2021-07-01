package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Article;
import cn.edu.ncepu.researchplatform.mapper.*;
import cn.hutool.core.io.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;


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
    @Autowired
    private MaterialMapper materialMapper;
    @Value("${customize.save-location}")
    private String pathPre;
    @Value("${customize.retain-day}")
    private Integer retainDay;

    @Scheduled(cron = "@daily")
    public void deleteInvalidStar() {
        starMapper.deleteInvalidStar();
    }

    @Scheduled(cron = "@daily")
    public void deleteIlleageEvaluate() {
        evaluateMapper.deleteIlleageEvaluate();
    }

    @Scheduled(cron = "@monthly")
    public void updatePeopleWeight() {
        peopleMapper.updateWeight();
    }

    @Scheduled(cron = "@monthly")
    public void updateArticleWeight() {
        articleMapper.updateWeight();
    }

    //待优化
    @Scheduled(cron = "@daily")
    public void updateArticleScore() {
        while (true) {
            Article needCalculateArticle = articleMapper.findCalculatedArticle();
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
    public void updateScoreItem() {
    }

    @Scheduled(cron = "@daily")
    public void deleteExpireArticleFile() {
        LocalDateTime time = LocalDateTime.now().minusDays(retainDay);
        articleMapper.findPathByDeleted(time);
        List<String> fileNames = FileUtil.listFileNames(Paths.get(pathPre, "ResearchPlatformFiles","article").toString());
        for (String file : fileNames) {
            if (FileUtil.isFile(file) && file.endsWith(".temp")) {
                FileUtil.del(file);
            }
        }
    }

    @Scheduled(cron = "@daily")
    public void deleteExpireMaterialFile() {
        LocalDateTime time = LocalDateTime.now().minusDays(retainDay);
        materialMapper.findPathByDeleted(time);
        List<String> fileNames = FileUtil.listFileNames(Paths.get(pathPre, "ResearchPlatformFiles","material").toString());
        for (String file : fileNames) {
            if (FileUtil.isFile(file) && file.endsWith(".temp")) {
                FileUtil.del(file);
            }
        }
    }

}
