package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.People
import cn.edu.ncepu.researchplatform.entity.dto.PeopleDto
import cn.edu.ncepu.researchplatform.security.PeopleDetails
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.One
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import org.apache.ibatis.mapping.FetchType
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
    WHEN -1 THEN "black"
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
SELECT DISTINCT
people.username AS username, 
people.nickname AS nickname, 
people.id_card AS id_card, 
people.realname AS realname, 
people.email AS email, 
people.gmt_create AS gmt_create, 
GROUP_CONCAT(area.`name`) AS area_string,
people.`level` as `level`
FROM
people_area
INNER JOIN
area
ON 
people_area.area_id = area.id
INNER JOIN
people
ON 
people.id = people_area.people_id 
        <where>
            <if test="!@cn.edu.ncepu.researchplatform.utils.Utils@isAdmin()">
                and auth !=-1
            </if>
           <if test="lc!=null">
                and gmt_create &gt;=#{lc}
            </if>
             <if test="rc!=null">
                and gmt_create &lt;=#{rc}
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
            <if test=" organization !=null  and organization !='' ">
               and  organization like #{organization}
            </if>
            <if test=" email !=null  and email !='' ">
               and  email like #{email}
            </if>
            <if test=" idCard !=null  and idCard !='' ">
               and  id_card like #{idCard}
            </if>
            <if test=" realname !=null  and realname !='' ">
               and  realname like #{realname}
            </if>
             <if test=" areaname !=null and areaname !=''  ">
              and   area.`name` like #{areaname}
            </if>
            <if test=" level !=null   ">
              and   level=#{level}
            </if>
             <if test=" auth !=null   ">
              and   auth=#{auth}
            </if>
        </where>
        GROUP BY people.username  , 
people.nickname , 
people.id_card , 
people.realname , 
people.email , 
people.gmt_create , 
people.info , 
people.`level` 
limit ${(current-1)*size},#{size}
</script>
''')
    List<People> findByCondition(PeopleDto dto);

    @Select('''
<script>
SELECT 
count(DISTINCT people.username)
FROM
people_area
INNER JOIN
area
ON 
people_area.area_id = area.id
INNER JOIN
people
ON 
people.id = people_area.people_id 
        <where>
            <if test="!@cn.edu.ncepu.researchplatform.utils.Utils@isAdmin()">
                and auth !=-1
            </if>
           <if test="lc!=null">
                and gmt_create &gt;=#{lc}
            </if>
             <if test="rc!=null">
                and gmt_create &lt;=#{rc}
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
            <if test=" organization !=null  and organization !='' ">
               and  organization like #{organization}
            </if>
            <if test=" email !=null  and email !='' ">
               and  email like #{email}
            </if>
            <if test=" idCard !=null  and idCard !='' ">
               and  id_card like #{idCard}
            </if>
            <if test=" realname !=null  and realname !='' ">
               and  realname like #{realname}
            </if>
             <if test=" areaname !=null and areaname !=''  ">
              and   area.`name` like #{areaname}
            </if>
            <if test=" level !=null   ">
              and   level=#{level}
            </if>
             <if test=" auth !=null   ">
              and   auth=#{auth}
            </if>
        </where>
</script>
''')
    Integer findCountByCondition(PeopleDto dto);

    @Select('select * from people where id=(select author_id from article where id=#{articleId})')
    People findAuthorByArticleId(Integer articleId);

    @Update('update `people` set auth=#{param2} where id=#{param1}')
    boolean updateAuth(Integer id, Integer auth);

    @Select('select icon from people where username=#{param1} ')
    String findIcon(String username);

    @Update('update `people` set icon=#{param2} where username=#{param1} ')
    boolean updateIcon(String username,String icon);

    @Results(id = "BasePeopleMap", value = [
            @Result(property = "id", column = "id", id = true),
            @Result(property = "author", column = "author_id", one = @One(select = 'cn.edu.ncepu.researchplatform.mapper.PeopleMapper.findById', fetchType = FetchType.EAGER)),
            @Result(property = "auth", column = "auth"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "weight", column = "weight"),
            @Result(property = "exp", column = "exp"),
            @Result(property = "level", column = "level"),
            @Result(property = "idCard", column = "id_card"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "organization", column = "organization"),
            @Result(property = "email", column = "email"),
            @Result(property = "info", column = "info"),
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtDelete", column = "gmt_delete"),
            @Result(property = "areas", column = "id", one = @One(select = 'cn.edu.ncepu.researchplatform.mapper.AreaMapper.findPeopleAreas', fetchType = FetchType.EAGER))
    ])
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

    @Select('select id,username,phone,nickname from `people` where id=#{param1}')
    People findById(Integer id);

    @Update('update `people` set exp=exp-#{param2} where id =#{param1} ')
    boolean cost(Integer poepleId, Integer value);

    @Update('update `people` set password=#{param2} where phone=#{param1}')
    boolean updatePass(String phone, String pass);

    @Insert('insert into `people`(username,phone,password) values(#{username},#{phone},#{password})')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    Integer insertPeople(People people);

    @Update('''
<script>
update `people` 
<set>  
        <if test="nickname!= null and nickname != '' ">  
               nickname  =#{nickname},
        </if>  
        <if test="organization!= null and organization!= '' ">  
           organization=#{organization},  
        </if>  
        <if test="email != null and email!= '' ">  
            email=#{email},  
        </if>  
        <if test="info != null and info!= '' ">  
            info=#{info} 
        </if>  
    </set> 
   where id=#{id}
</script>
''')
    boolean updateInfo(People people);

    @Update('update `people` set realname=#{param1},id_card=#{param2}  where username=#{param3} and auth!=-1 and id_card is null ')
    boolean updateReal(String realname, String idCard, String username);

    @Update('update `people` set phone=#{param1}  where username=#{param2}')
    boolean updatePhone(String phone, String username);

    @Update('update `people` set weight=weight+#{param1}')
    boolean updateWeight(Integer incr);

    @Update('update `people` set exp=exp+rand()*300')
    boolean updateExp();

    @Select('select * from `people` where auth!=2 and auth!=3 order by `exp` desc limit 100')
    List<People> rankPeople();
}