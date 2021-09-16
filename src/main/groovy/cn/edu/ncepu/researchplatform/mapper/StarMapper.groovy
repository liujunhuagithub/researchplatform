package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Star
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
interface StarMapper {
    @Insert('insert into `star`(people_id,evaluate_id,flag) values(#{param2},#{param1},#{param3})')
    boolean saveStar(Integer evaluateId, Integer peopleId, Integer flag);

    @Update('update `star` set flag=#{param3} where evaluate_id=#{param1} and people_id =#{param2}')
    boolean updateStar(Integer evaluateId, Integer peopleId, Integer flag);

    @Select('select * from `star` where evaluate_id=#{param1} and people_id =#{param2} ')
    Star findOne(Integer evaluateId, Integer peopleId);
@Delete('delete from `star` where flag=0')
    boolean deleteInvalidStar();

    @Update('update `evaluate` set star=star+#{param2}  where id=#{param1}')
    boolean updateEvaluateStar(Integer evaluateId,Integer flag);
}




