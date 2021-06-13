package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Summary
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
interface SummaryMapper {
    insert(Summary summary);
}




