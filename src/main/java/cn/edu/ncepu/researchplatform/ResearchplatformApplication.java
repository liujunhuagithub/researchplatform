package cn.edu.ncepu.researchplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class ResearchplatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResearchplatformApplication.class, args);
    }

}
