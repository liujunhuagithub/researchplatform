package cn.edu.ncepu.researchplatform.config;

import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class StartUpCheckConfig implements ApplicationRunner {
    private final static Logger logger = LoggerFactory.getLogger(StartUpCheckConfig.class);
    @Autowired
    private PeopleMapper peopleMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.debug("开始启动************************");
        peopleMapper.initRoot();
    }
}
