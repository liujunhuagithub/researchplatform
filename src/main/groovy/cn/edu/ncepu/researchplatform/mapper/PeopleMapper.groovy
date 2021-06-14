package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Article
import cn.edu.ncepu.researchplatform.entity.People
import cn.edu.ncepu.researchplatform.entity.dto.PeopleDto
import cn.edu.ncepu.researchplatform.security.PeopleDetails
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Options
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
    WHEN 3 THEN "admin,vip"
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

    @Select('''
<script>
select distinct * from `people`
        <where>
           <if test="lc!=null">
                and gmt_create &gt;=#{lc}
            </if>
             <if test="rc!=null">
                and gmt_create &lt;=#{rc}
            </if>
           <if test="ld!=null">
                and gmt_delete &gt;=#{ld}
            </if>
             <if test="rd!=null">
                and gmt_delete &lt;=#{rd}
            </if>
            <if test="username !=null and username !=''">
                and `username` like #{username}
            </if>
            <if test=" phone !=null  and phone !='' ">
                and phone  like #{phone}
            </if>
            <if test=" nickname !=null  and nickname !='' ">
               and  nickname like #{nickname}
            </if>
            <if test=" idCard !=null  and idCard !='' ">
               and  id_card=#{idCard}
            </if>
            <if test=" realname !=null  and realname !='' ">
               and  realname like #{realname}
            </if>
            <if test=" level !=null   ">
              and   level=#{level}
            </if>
        </where>
</script>
''')
    List<People> findByCondition(PeopleDto dto);


    @Select('select * from people where id=(select author_id from article where id=#{articleId})')
    People findAuthorByArticleId(Integer articleId);

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
    WHEN 3 THEN "admin,vip"
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

    @Select('select * from `people` where id=#{param1}')
    People findById(Integer id);

    @Update('update `people` set exp=exp-#{param1} ')
    boolean cost(Integer value);

    @Update('update `people` set password=#{param2} where phone=#{param1} and gmt_delete is null')
    boolean updatePass(String phone, String pass);

    @Update('update `people` set gmt_delete=null where auth=2')
    void deleteBlackPeople();

    @Insert('insert into `people`(username,phone,password) values(#{username},#{phone},#{password})')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    Integer insertPeople(People people);

    @Update('update `people` set nickname=#{nickname}, organization=#{organization},email=#{email},info=#{info} where gmt_delete is null and id=#{id}')
    boolean updateInfo(People people);

    @Update('update `people` set realneme=#{param1},id_card=#{param2}  where gmt_delete is null and username=#{param3} and auth!=2 and not exist (select * from `people` where id_card=#{param2} and auth=2)')
    boolean updateReal(String realname, String idCard, String username);

    @Update('update `people` set phone=#{phone}  where gmt_delete is null and where gmt_delete is null and username=#{param2}')
    boolean updatePhone(String phone, String username);
    @Update('update `people` set weight+=(random()-0.3)')
    boolean updateWeight();

    @Select('select * from `people` order by rand() limit 100')
    List<People> rankPeople();
}