package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Evaluate
import cn.edu.ncepu.researchplatform.entity.dto.EvaluateDto
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository

@Repository
interface EvaluateMapper {
    @Update('update `evaluate` set `gmt_delete`=CURRENT_TIMESTAMP where id=#{param1} and `people_id`=#{param2}')
    boolean deleteByIdAndPeopleId(Integer id, Integer peopleId);

    @Update('update `evaluate` set flag=#{param2} where id = #{param1}')
    boolean updateFlag(Integer id, Integer flag);

    @Select('select * from `evaluate` where id =#{param1}')
    Evaluate findById(Integer id);

    @Update('update  `evaluate` set `gmt_delete`=CURRENT_TIMESTAMP  where `gmt_delete` IS NULL and article_id in (select id from `article` where gmt_delete is not null)')
    Integer deleteArticleAboutEvaluate();

    @Select('select evaluate_id from `star` where flag!=0 and people_id=#{param2} and evaluate_id in (select id from `evaluate` where article_id= #{param1} and gmt_delete is null )')
    List<Integer> findBypeopleToArticle(Integer articleId, Integer poepleId);

    @Select('''<script>
select * from `evaluate` 
<where>
article_id= #{param1} and gmt_delete is null and parent_id=0
<if test="!param2">
and niming=0
</if>
</where>
order by `flag` desc
limit ${(current-1)*size},#{size}
</script>''')
    List<Evaluate> findEvaluateByArticleId(Integer articleId, boolean isAuthor, @Param("current") Integer current, @Param("size") Integer size);

    @Select('''<script>
select * from `evaluate` 
<where>
<if test="flag!=null">
and flag=#{flag}
</if>
<if test="articleId!=null">
and articleId=#{articleId}
</if>
<if test="peopleId!=null">
and peopleId=#{peopleId}
</if>
and gmt_delete is null
</where>
limit ${(current-1)*size},#{size}
</script>''')
    List<Evaluate> findByPage(EvaluateDto dto)
    @Select('''<script>
select count(id) from `evaluate` 
<where>
<if test="flag!=null">
and flag=#{flag}
</if>
<if test="articleId!=null">
and articleId=#{articleId}
</if>
<if test="peopleId!=null">
and peopleId=#{peopleId}
</if>
and gmt_delete is null
</where>
</script>''')
    Integer findByPageCount(EvaluateDto dto)

    @Select('select * from `evaluate` where gmt_delete is null and parent_id=#{param1} limit ${(current-1)*size},#{size}')
    List<Evaluate> findDisscussByParentId(Integer parentId, @Param("current") Integer current, @Param("size") Integer size);


    @Select('select * from `evaluate` where gmt_delete is null and people_id=#{param1} limit ${(current-1)*size},#{size} ')
    List<Evaluate> findByPeopleId(Integer peopleId, @Param("current") Integer current, @Param("size") Integer size);

    @Select('select count(id) from `evaluate` where gmt_delete is null and people_id=#{param1} ')
    Integer findCountByPeopleId(Integer peopleId);

    @Insert('insert into `evaluate`(people_id,article_id,niming,parent_id,content) values(#{peopleId},#{articleId},#{niming},#{parentId},#{content})')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    Integer insertEvaluate(Evaluate evaluate);


    @Insert('insert into `evaluate`(people_id,article_id,parent_id,path) values(#{peopleId},#{articleId},},#{parentId},#{path})')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    Integer insertDiscuss(Evaluate evaluate);

    @Select('select * from `evaluate` where flag in (0,1) order by `score_item` limit 100')
    List<Evaluate> rankEvaluate();

    @Update('update evaluate set weight+=(random()-0.3)')
    boolean updateWeight();

    @Update('update `article` set calculate=#{param2} where id=#{param1}')
    boolean updateArticleCalculateByArticleId(Integer articleId, Integer flag);

    @Update('update `article` set calculate=#{param2} where id=(select article_id from evaluate where id=#{param1})')
    boolean updateArticleCalculateByEvaluateId(Integer evaluateId, Integer flag);

    @Update('update evaluate set gmt_delete=CURRENT_TIMESTAMP where id=#{param1}')
    boolean deleteById(Integer evaluateId);

    @Update('update evaluate set gmt_delete=CURRENT_TIMESTAMP where flag=-1 and gmt_delete is null')
    boolean deleteIlleageEvaluate();
    @Update('update  evaluate set score_item=((rand()-0.5)*star)')
    boolean updateScoreItem()
}




