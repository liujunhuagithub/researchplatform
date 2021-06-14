package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Summary
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

@Repository
interface SummaryMapper {
    @Insert('insert into `summary`(people_id,content) values (#{peopleId},#{content}) ')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    Integer insert(Summary summary);
}




