package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Article
import cn.edu.ncepu.researchplatform.entity.Evaluate
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

    @Select('select * from where id =#{param1}')
    Evaluate findById(Integer id);

    @Update('update  `evaluate` set `gmt_delete`=CURRENT_TIMESTAMP  where `gmt_delete` IS NULL and article_id in (select id from `article` where gmt_delete is not null)')
    Integer deleteArticleAboutEvaluate();

    @Select('select evaluate_id from `star` where poeple_id=#{param2} and evaluate_id in (select id from `evaluate` where article_id= #{articleId} and gmt_delete is null )')
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
 gmt_delete is null and parent_id=#{param1}
<if test="!isAuthor">
niming=0
</if>
</where>
limit ${(current-1)*size},#{size}
</script>''')
    List<Evaluate> findDisscussByParentId(Integer parentId, @Param("current") Integer current, @Param("size") Integer size);

    @Insert('insert into `evaluate`(people_id,article_id,summary_id,niming,parent_id,content) values(#{peopleId},#{articleId},#{summaryId},#{niming},#{parentId},#{content})')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    Integer insertEvaluate(Evaluate evaluate);

    @Insert('insert into `evaluate`(people_id,article_id,parent_id,content) values(#{peopleId},#{articleId},},#{parentId},#{content})')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    Integer insertDiscuss(Evaluate evaluate);

    @Select('select * from `evaluate` order by `score_item` limit 100')
    List<Evaluate> rankEvaluate();

    @Update('update evaluate set weight+=(random()-0.3)')
    boolean updateWeight();

    @Update('update `article` set calculate=#{param2} where id=#{param1}')
    boolean updateArticleCalculateByArticleId(Integer articleId, Integer flag);

    @Update('update `article` set calculate=#{param2} where id=(select article_id from evaluate where id=#{param1})')
    boolean updateArticleCalculateByEvaluateId(Integer evaluateId, Integer flag);

    @Update('update evaluate set gmt_delete=CURRENT_TIMESTAMP where id=#{param1}')
    boolean deleteById(Integer evaluateId);
}




