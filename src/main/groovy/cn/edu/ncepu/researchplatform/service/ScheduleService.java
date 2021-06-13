package cn.edu.ncepu.researchplatform.service;

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
    private StarMapper starMapper;
    @Autowired
    private EvaluateMapper evaluateMapper;
    @Autowired
    private PeopleMapper peopleMapper;

    @Scheduled(cron = "@weekly")
    public void peoples() {
    }

    @Scheduled(cron = "@monthly")
    public void updatePeopleWeight() {

    }

    @Scheduled(cron = "@weekly")
    public void updateArticleScore() {

    }

    @Scheduled(cron = "@daily")
    public void deleteArticleAboutEvaluate() {
        evaluateMapper.deleteArticleAboutEvaluate();
    }

    @Scheduled(cron = "@daily")
    public void deleteInvalidStar() {
        starMapper.deleteInvalidStar();
    }

    @Scheduled(cron = "@daily")
    public void deleteBlackPeople() {
        peopleMapper.deleteBlackPeople();
    }
}
