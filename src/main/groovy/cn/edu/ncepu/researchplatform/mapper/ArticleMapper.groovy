package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Area
import cn.edu.ncepu.researchplatform.entity.Article
import cn.edu.ncepu.researchplatform.entity.People
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
interface ArticleMapper {
    @Select('select * from `article` where  id=#{param1}' )
    Article findById(Integer id);

    List<Article> findToDisplay(Integer flag);


    @Update('update `article` set `flag`=#{param1} where id=#{param2}')
    boolean updateFlag(Integer flag, Integer articleId);

   @Update('update `article` set `gmt_delete`=CURRENT_TIMESTAMP where id=#{param1} and `author_id`=#{param2}')
   boolean deleteByIdAuthorId(Integer id,Integer authorId);
}




