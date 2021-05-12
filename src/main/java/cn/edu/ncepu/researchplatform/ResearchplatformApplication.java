package cn.edu.ncepu.researchplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
@MapperScan("cn.edu.ncepu.researchplatform.dao")
public class ResearchplatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResearchplatformApplication.class, args);
    }

}
