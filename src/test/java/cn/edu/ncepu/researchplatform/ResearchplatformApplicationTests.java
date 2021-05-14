package cn.edu.ncepu.researchplatform;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import cn.hutool.core.util.PhoneUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
class ResearchplatformApplicationTests {
    @Autowired
    AreaMapper areaMapper;
@Autowired
    PeopleMapper peopleMapper;
    @Test
    @Rollback(value = false)
    void contextLoads() {
        PeopleDetails obj = peopleMapper.findByUsername(1234);
        System.out.println(obj);    }

}
