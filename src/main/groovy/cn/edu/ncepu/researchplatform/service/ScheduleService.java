package cn.edu.ncepu.researchplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class ScheduleService {
    @Autowired
    private RankService rankService;

    @Scheduled(cron = "@weekly")
    public void peoples() {
    }
}
