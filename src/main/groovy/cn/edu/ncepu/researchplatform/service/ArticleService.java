package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Article;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private PeopleMapper peopleMapper;

    public boolean updateFlag(Integer flag, Integer articleId) {
        return articleMapper.updateFlag(flag, articleId);
    }

    public boolean deleteByIdAuthorId(Integer id, String username) {
        return articleMapper.deleteByIdAuthorId(id, peopleService.findByUsername(username).getId());
    }

    @Cacheable(value = "article", key = "#articleId")
    public Article findArticleById(Integer articleId) {
        Article article = articleMapper.findById(articleId);
        article.setAuthorName(peopleMapper.findAuthorById(article.getAuthorId()).getNickname());
        article.setAreas(areaMapper.findArticleAreas(article.getId()));
        return article;
    }
}
