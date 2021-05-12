package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Area
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Update
import org.springframework.stereotype.Repository

@Repository
interface AreaMapper {
    @Insert('insert into `area` values(#{param1.name},#{param1.parentId})')
    Area insertArea(Area area);

    @Update('update `area` set active')
    boolean disableArea(Integer id);
}
