package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Article
import cn.edu.ncepu.researchplatform.entity.dto.ArticleDto
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.One
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository

import java.time.LocalDateTime;

@Repository
interface ArticleMapper {

    @Results(id = "BaseArticleMap", value = [
            @Result(property = "id", column = "id", id = true),
            @Result(property = "author", column = "author_id", one = @One(select = 'cn.edu.ncepu.researchplatform.mapper.PeopleMapper.findById', fetchType = FetchType.EAGER)),
            @Result(property = "path", column = "path"),
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtDelete", column = "gmt_delete"),
            @Result(property = "areas", column = "id", one = @One(select = 'cn.edu.ncepu.researchplatform.mapper.AreaMapper.findArticleAreas', fetchType = FetchType.EAGER))
    ])
    @Select('select * from `article` where id=#{param1}')
    Article findById(Integer id);

    @Select('select article_id from `evaluate` where id=#{param1}')
    Integer findIdaboutEvaluate(Integer evaluateId);

    @Update('update `article` set `flag`=#{param1} where id=#{param2}')
    boolean updateFlag(Integer flag, Integer articleId);

    @Update('update `article` set `gmt_delete`=CURRENT_TIMESTAMP where id=#{param1} and `author_id`=#{param2}')
    boolean deleteByIdAuthorId(Integer id, Integer authorId);

    @Insert('insert into `article`(author_id,title,abstracts,ref,path) values(#{authorId},#{title},#{abstracts},#{ref},#{path})')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    void insert(Article article);

    @Insert('insert into `article_area`(article_id,area_id) values(#{param1},#{param2})')
    boolean insertArea(@Param("articleId")Integer articleId, Integer areaId);

    @Select('''
<script>
select * from `article`
        <where>
            <if test="!@cn.edu.ncepu.researchplatform.utils.Utils@isAdmin()">
                and gmt_delete is null
            </if>
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
            
            <if test="title !=null and title !=''">
                and `title` like concat('%',#{title},'%')
            </if>
            <if test=" flag !=null ">
              and   flag=#{flag}
            </if>            
            <if test=" authorId !=null ">
              and   author_id=#{authorId}
            </if>
        </where>
        limit ${(current-1)*size},#{size}
</script>
''')
    List<Article> findByCondition(ArticleDto dto);

    @Select('''
<script>
select count(id) from `article`
        <where>
             <if test="!@cn.edu.ncepu.researchplatform.utils.Utils@isAdmin()">
                and gmt_delete is null
            </if>
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
            
            <if test="title !=null and title !=''">
                and `title` like concat('%',#{title},'%')
            </if>
            <if test=" flag !=null ">
              and   flag=#{flag}
            </if>            
            <if test=" authorId !=null ">
              and   author_id=#{authorId}
            </if>
        </where>
</script>
''')
    Integer findCountByCondition(ArticleDto dto);

    @Select('select * from `article` order by `score` limit 100')
    List<Article> rankArticle();

    @Select('select * from `article`  where calculate=1 order by rand() limit 1')
    Article findCalculatedArticle();

    @Update('update `article` set score=(select sum(score_item) from evaluate where article_id=#{param1} and gmt_delete is null)')
    boolean updateScore(Integer articleId);

    @Update('update `article` set weight+=#{param1}')
    boolean updateWeight(Integer incr);

    @Select('select `path` from `article` where gmt_delete <=#{param1}')
    List<String> findPathByDeleted(LocalDateTime time);

    @Select(' select * from `article`  where id in (select article_id from article_area where area_id=#{areaId}) order by rand() limit  #{size} ' )
    List<Article> findByPersonalise(ArticleDto dto)
}




