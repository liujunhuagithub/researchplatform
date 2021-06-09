package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.People
import cn.edu.ncepu.researchplatform.security.PeopleDetails
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
interface PeopleMapper {

    //0未认证1已认证2已封号3管理员
    @Select('''
    <script>
    select id,CONCAT(username,'') AS username,password,CONCAT(phone,'') AS phone,
    CASE auth
    WHEN 0 THEN "guest"
    WHEN 1 THEN "vip"
    WHEN 2 THEN "black"
    WHEN 3 THEN "admin"
    END AS auth
    FROM `people`
<where>
<if test="!@cn.hutool.core.util.PhoneUtil@isMobile(username+'')">
username =#{username}
</if>
<if test="@cn.hutool.core.util.PhoneUtil@isMobile(username+'')">
and phone =#{username}
</if>
</where>
 </script> ''')
    PeopleDetails findByUsername(Integer username);

}