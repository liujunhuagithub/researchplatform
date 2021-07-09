package cn.edu.ncepu.researchplatform.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUpCheckConfig implements ApplicationRunner {
    private final static Logger logger = LoggerFactory.getLogger(StartUpCheckConfig.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
logger.debug("开始启动************************");
    }
}
