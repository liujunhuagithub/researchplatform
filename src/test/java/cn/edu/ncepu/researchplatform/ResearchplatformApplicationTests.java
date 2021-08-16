package cn.edu.ncepu.researchplatform;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import cn.edu.ncepu.researchplatform.service.AreaService;
import cn.edu.ncepu.researchplatform.service.ScheduleService;
import cn.hutool.core.util.PhoneUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    @Qualifier("customizerPasseordEncoder")
    private PasswordEncoder passwordEncoder;
    @Autowired
    AreaService areaService;
    @Test
    @Rollback(value = false)
    void contextLoads() throws JsonProcessingException {
        System.out.println("----------");
        Integer[] integers = om.readValue("[12]", Integer[].class);
        System.out.println(passwordEncoder.encode("root"));
    }

}
