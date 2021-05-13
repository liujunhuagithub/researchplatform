package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Area
import cn.edu.ncepu.researchplatform.entity.Article
import cn.edu.ncepu.researchplatform.entity.People
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
 interface ArticleMapper {
 Article findById(Integer id);
@Select('select * from people where id=(select author from article where id=#{articleId})')
 People findAuthorById(Integer articleId);
}




