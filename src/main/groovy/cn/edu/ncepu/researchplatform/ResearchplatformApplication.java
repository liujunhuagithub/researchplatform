package cn.edu.ncepu.researchplatform;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

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
