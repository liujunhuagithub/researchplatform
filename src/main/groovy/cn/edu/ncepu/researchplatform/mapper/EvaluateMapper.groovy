package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Evaluate
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
 interface EvaluateMapper  {
 @Update('update `evaluate` set `gmt_delete`=CURRENT_TIMESTAMP where id=#{param1} and `people_id`=#{param2}')
 boolean deleteByIdPeopleId(Integer id,Integer peopleId);

@Update('update `evaluate` set flag=#{param2} where id = #{param1}')
 boolean updateFlag(Integer id,Integer flag);
@Select('select * from where id =#{param1}')
    Evaluate findById(Integer id);
    @Update('update  `evaluate` set `gmt_delete`=CURRENT_TIMESTAMP  where article_id in (select id from `article` where gmt_delete is not null)')
    Integer deleteArticleAboutEvaluate();
}




