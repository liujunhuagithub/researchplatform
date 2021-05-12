package cn.edu.ncepu.researchplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@EnableCaching
@SpringBootApplication
@MapperScan("cn.edu.ncepu.researchplatform.mapper")
public class ResearchplatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResearchplatformApplication.class, args);
    }

}
