package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.entity.Article;
import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import cn.edu.ncepu.researchplatform.service.ArticleService;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private PeopleService peopleService;
    //    @PostAuthorize("#username==#authentication.name or hasAuthority('admin')")
    @DeleteMapping("/people/{username}/article/{articleId}")
    public boolean 删除article(@PathVariable String username, @PathVariable Integer articleId) {
        return articleService.deleteByIdAuthorId(articleId, username);
    }

    @GetMapping("article/{id}")
    public Article 查询某article(@PathVariable Integer id) {
        Article article = articleService.findArticleById(id);
        //   普通用户                  已删除                           有问题
        if (!Utils.isAdmin()&&(article.getGmtDelete() != null||!article.getFlag().equals(1))) {
            return null;
        }
        return article;
    }
    @PostMapping("/article")
    public Integer 新增article(Article article){
        article.setAuthorId(peopleService.findByUsername(Utils.getCurrent()).getId());
        return articleService.insert(article);
    }
}
