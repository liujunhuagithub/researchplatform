package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.People
import cn.edu.ncepu.researchplatform.security.PeopleDetails
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
interface PeopleMapper {

    //0未认证1已认证2已封号3管理员
    @Select('''select id,CONCAT(username,''),password,
    CASE auth
    WHEN 0 THEN "guest"
    WHEN 1 THEN "vip"
    WHEN 2 THEN "black"
    WHEN 3 THEN "vip,admin"
    END
    FROM `people`
 where username =#{username} ''')
    PeopleDetails findByUsername(String username);
}