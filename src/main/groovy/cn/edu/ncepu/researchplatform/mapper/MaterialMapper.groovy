package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Material
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
interface MaterialMapper {
    @Insert('insert into `material`(people_id,content,area) values (#{people_id},#{content},#{area}) ')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    Integer insertMaterial(Material material);

    boolean throughArea(Integer peopleId, Integer areaId);

    @Update('update `material` set flag=flag where id=#{param1}')
    boolean updateFlag(Integer materialId, Integer flag);

    @Update('update `material` set gmt_delete=CURRENT_TIMESTAMP where id=#{param1} and people_id=#{param2}')
    boolean deleteById(Integer id, Integer peopleId);

    @Select('select * from `material` where id=#{param1}')
    Material findById(Integer id);
}




