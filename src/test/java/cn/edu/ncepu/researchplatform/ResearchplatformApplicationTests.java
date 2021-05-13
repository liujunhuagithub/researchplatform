package cn.edu.ncepu.researchplatform;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class ResearchplatformApplicationTests {
@Autowired
    AreaMapper areaMapper;
    @Test
    @Rollback(value = false)
    void contextLoads() {
        Area area = new Area();
        area.setName("1");
        area.setParentId(0);
        System.out.println(areaMapper.insertArea(area));
    }

}
