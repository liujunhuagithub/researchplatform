package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Material
import cn.edu.ncepu.researchplatform.entity.dto.MaterialDto
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository

import java.time.LocalDateTime;

@Repository
interface MaterialMapper {
    @Insert('insert into `material`(people_id,path,area_temp) values (#{peopleId},#{path},#{areaTemp}) ')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    Integer insertMaterial(Material material);

    @Insert('insert into `people_area` values (#{param1},#{param2})')
    boolean throughArea(Integer peopleId, Integer areaId);

    @Update('update `material` set flag=#{param2} where id=#{param1}')
    boolean updateFlag(Integer materialId, Integer flag);

    @Delete('delete from `material`  where id=#{param1} and people_id=#{param2}')
    boolean deleteById(Integer id, Integer peopleId);

    @Select('select * from `material` where id=#{param1}')
    Material findById(Integer id);

    @Select('select * from `material` where people_id=#{param1}')
    List<Material> findByPeopleId(Integer PeopleId);

    @Select('''
<script>
select * from `material`
<where>
    <if test=" flag !=null ">
       flag=#{flag}
    </if>
</where>
limit ${(current-1)*size},#{size}
</script>''')
    List<Material> findByCondition(MaterialDto dto);

    @Select('''
<script>
select count(id) from `material` 
<where>
    <if test=" flag !=null ">
       flag=#{flag}
    </if>
</where>
</script>''')
    Integer findCountByCondition(MaterialDto dto);
}




