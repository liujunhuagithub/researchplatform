package cn.edu.ncepu.researchplatform;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import cn.hutool.core.util.PhoneUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
class ResearchplatformApplicationTests {
    @Autowired
    AreaMapper areaMapper;
    @Autowired
    PeopleMapper peopleMapper;
    @Autowired
    ObjectMapper om;
    @Value("${customize.save-location}")
    private String pathPre;
    @Test
    @Rollback(value = false)
    void contextLoads() throws JsonProcessingException {
        System.out.println("----------------------");
        System.out.println(Paths.get(pathPre,"/article","/gjfno"));
    }

}
