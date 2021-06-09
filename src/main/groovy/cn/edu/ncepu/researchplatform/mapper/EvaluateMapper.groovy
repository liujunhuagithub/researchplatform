package cn.edu.ncepu.researchplatform.mapper

import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
 interface EvaluateMapper  {
 @Update('update `evaluate` set `gmt_delete`=CURRENT_TIMESTAMP where id=#{param1} and `people_id`=#{param2}')
 boolean deleteByIdPeopleId(Integer id,Integer peopleId);

}




