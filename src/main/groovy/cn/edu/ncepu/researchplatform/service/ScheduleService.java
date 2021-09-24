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
import java.util.Random;


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

    @Scheduled(cron = "@daily")
    public void updatePeopleWeight() {
        peopleMapper.updateWeight(new Random().nextInt(10) - 5);
    } @Scheduled(cron = "@daily")
    public void updatePeopleExp() {
        peopleMapper.updateExp();
    }

    @Scheduled(cron = "@daily")
    public void updateArticleWeight() {
        articleMapper.updateWeight(new Random().nextInt(10) - 5);
    }


    @Scheduled(cron = "@daily")
    public void updateArticleScore() {
        articleMapper.findCalculatedArticle().forEach(needCalculateArticle -> {
            articleMapper.updateScore(needCalculateArticle.getId());
            evaluateMapper.updateArticleCalculateByArticleId(needCalculateArticle.getId(), 0);
        });
    }

    @Scheduled(cron = "@daily")
    public void deleteArticleAboutEvaluate() {
        evaluateMapper.deleteArticleAboutEvaluate();
    }

    @Scheduled(cron = "@daily")
    public void updateScoreItem() {
        evaluateMapper.updateScoreItem();
    }

    @Scheduled(cron = "@daily")
    public void deleteExpireArticleFile() {
        LocalDateTime time = LocalDateTime.now().minusDays(retainDay);
        List<String> expiredArticle = articleMapper.findPathByDeleted(time);
        expiredArticle.forEach(s -> Paths.get(pathPre, "ResearchPlatformFiles", "article", s).toFile().delete());
        File[] tempFiles = Paths.get(pathPre, "ResearchPlatformFiles", "article").toFile().listFiles(pathname -> pathname.isFile() && pathname.getName().endsWith(".temp"));
        if (tempFiles == null) {
            return;
        }
        for (File file : tempFiles) {
            file.delete();
        }
    }

    @Scheduled(cron = "@daily")
    public void deleteExpireMaterialFile() {
        File[] tempFiles = Paths.get(pathPre, "ResearchPlatformFiles", "material").toFile().listFiles(pathname -> pathname.isFile() && pathname.getName().endsWith(".temp"));
        if (tempFiles == null) {
            return;
        }
        for (File file : tempFiles) {
            FileUtil.del(file);
        }
    }

}
