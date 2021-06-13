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

    @Scheduled(cron = "@daily")
    public void deleteInvalidStar() {
        starMapper.deleteInvalidStar();
    }

    @Scheduled(cron = "@monthly")
    public void updatePeopleWeight() {
        peopleMapper.updateWeight();
    }

    @Scheduled(cron = "@monthly")
    public void updateArticleScore() {

    }

    @Scheduled(cron = "@weekly")
    public void deleteArticleAboutEvaluate() {
        evaluateMapper.deleteArticleAboutEvaluate();
    }

    @Scheduled(cron = "@weekly")
    public void deleteBlackPeople() {
        peopleMapper.deleteBlackPeople();
    }
}
