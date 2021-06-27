package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.entity.Article;
import cn.edu.ncepu.researchplatform.entity.dto.ArticleDto;
import cn.edu.ncepu.researchplatform.entity.vo.ArticleVo;
import cn.edu.ncepu.researchplatform.service.ArticleService;
import cn.edu.ncepu.researchplatform.service.OtherService;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private PeopleService peopleService;

    @PreAuthorize("#username== authentication.name or hasAuthority('admin')")
    @DeleteMapping("/people/{username}/article/{articleId}")
    public boolean 删除article路径参数username必须是文章作者而非当前登录用户(@PathVariable String username, @PathVariable Integer articleId) {
        return articleService.deleteByIdAuthorId(articleId, username);
    }

    @GetMapping("/article/{id}")
    public ArticleVo 查询某article(@PathVariable Integer id) {
        Article article = articleService.findArticleById(id);
        //   普通用户                  已删除                           有问题
        if (!Utils.isAdmin() && (article.getGmtDelete() != null || !article.getFlag().equals(1))) {
            return null;
        }
        return new ArticleVo(article, peopleService.findById(article.getId()).getUsername());
    }

    @GetMapping("/article")
    public List<Article> 条件查询(ArticleDto dto) {
        return articleService.findByCondition(dto);
    }

    @PostMapping("/article")
    public Integer 新增article(Article article) {
        if (OtherService.isIllegalArticle(article.getContent())) {
            throw CustomException.SENSITIVE_ERROR_Exception;
        }
        article.setAuthorId(peopleService.findByUsername(Utils.getCurrent()).getId());
        return articleService.insert(article);
    }
}
