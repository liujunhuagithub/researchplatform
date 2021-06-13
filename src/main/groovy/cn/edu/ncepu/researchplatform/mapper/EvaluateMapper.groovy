package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Article
import cn.edu.ncepu.researchplatform.entity.Evaluate
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository

@Repository
interface EvaluateMapper {
    @Update('update `evaluate` set `gmt_delete`=CURRENT_TIMESTAMP where id=#{param1} and `people_id`=#{param2}')
    boolean deleteByIdPeopleId(Integer id, Integer peopleId);

    @Update('update `evaluate` set flag=#{param2} where id = #{param1}')
    boolean updateFlag(Integer id, Integer flag);

    @Select('select * from where id =#{param1}')
    Evaluate findById(Integer id);

    @Update('update  `evaluate` set `gmt_delete`=CURRENT_TIMESTAMP  where article_id in (select id from `article` where gmt_delete is not null)')
    Integer deleteArticleAboutEvaluate();

    @Select('select evaluate_id from `star` where poeple_id=#{param2} and evaluate_id in (select id from `evaluate` where article_id= #{articleId} and gmt_delete is null )')
    List<Integer> findBypeopleToArticle(Integer articleId, Integer poepleId);

    @Select('''<script>
select * from `evaluate` 
<where>
article_id= #{articleId} and gmt_delete is null 
<if test="!isAuthor">
niming=0
</if>
</where>
</script>''')
    List<Evaluate> findByArticleId(Integer articleId, boolean isAuthor);

    @Insert('insert into `evaluate`(people_id,article_id,summary_id,niming,parent_id,content) values(#{peopleId},#{articleId},#{summaryId},#{niming},#{parentId},#{content})')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    Integer insert(Evaluate evaluate);
}




