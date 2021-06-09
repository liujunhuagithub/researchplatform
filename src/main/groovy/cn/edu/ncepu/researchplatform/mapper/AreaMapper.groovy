package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Area
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import org.springframework.stereotype.Repository

@Repository
interface AreaMapper {

    @Select('select * from `area` where id in (select area_id from `article_area` where article_id=#{param1})')
    List<Area> findArticleAreas(Integer articleId);

    @Select('select * from `area`')
    List<Area> findAllArea();
    @Insert('insert into `area`(`name`,`parent_id`) values(#{name},#{parentId})')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    Integer insertArea(Area area);

    @Update('update `area` set `name`=#{param1} where id=#{param2}')
    boolean updateName(String name,Integer id);
}
