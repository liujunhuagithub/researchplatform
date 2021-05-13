package cn.edu.ncepu.researchplatform;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
class ResearchplatformApplicationTests {
@Autowired
    AreaMapper areaMapper;
    @Test
    @Rollback(value = false)
    void contextLoads() {

    }

}
