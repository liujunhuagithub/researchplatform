package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.People
import cn.edu.ncepu.researchplatform.security.PeopleDetails
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import org.springframework.stereotype.Repository

@Repository
interface PeopleMapper {

    //0未认证1已认证2已封号3管理员
    @Select('''
    <script>
    select id, username,password, phone,
    CASE auth
    WHEN 0 THEN "guest"
    WHEN 1 THEN "vip"
    WHEN 2 THEN "black"
    WHEN 3 THEN "admin"
    END AS auth
    FROM `people`
<where>
<if test="!@cn.hutool.core.util.PhoneUtil@isMobile(username)">
username =#{username} 
</if>
<if test="@cn.hutool.core.util.PhoneUtil@isMobile(username)">
and phone =#{username}
</if>
</where>
 </script> ''')
    PeopleDetails findByUsername(String username);

    @Select('select * from people where id=(select author_id from article where id=#{articleId})')
    People findAuthorById(Integer articleId);

    @Update('update `people` set auth=#{param2} where id=#{param1}')
    boolean updateAuth(Integer id, Integer auth);

    @Select('select icon from people where username=#{param1} ')
    String findIcon(String username);

    @Select('''SELECT
people.id AS id, 
 people.username AS username, 
CASE auth
    WHEN 0 THEN "guest"
    WHEN 1 THEN "vip"
    WHEN 2 THEN "black"
    WHEN 3 THEN "admin"
    END AS auth, 
people.phone AS phone, 
people.nickname AS nickname, 
people.weight AS weight, 
people.exp AS exp, 
people.`level` AS `level`, 
people.id_card AS id_card, 
people.realname AS realname, 
people.organization AS organization, 
people.email AS email, 
people.info AS info, 
people.gmt_create AS gmt_create
FROM
`people` where username=#{param1} ''')
    People findALLInfo(String username);
}