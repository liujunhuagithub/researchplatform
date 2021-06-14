package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Article;
import cn.edu.ncepu.researchplatform.entity.dto.ArticleDto;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIdAuthorId(Integer id, String username) {
        return articleMapper.deleteByIdAuthorId(id, peopleService.findByUsername(username).getId());
    }

    @Cacheable(value = "article", key = "#articleId")
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Article findArticleById(Integer articleId) {
        Article article = articleMapper.findById(articleId);
        article.setAuthorName(peopleMapper.findAuthorByArticleId(article.getAuthorId()).getNickname());
        article.setAreas(areaMapper.findArticleAreas(article.getId()));
        return article;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(Article article) {
        article.getAreas().forEach(a -> articleMapper.insertArea(article.getId(), a.getId()));
        return articleMapper.insert(article);
    }

    public List<Article> findByCondition(ArticleDto dto) {
        return articleMapper.findByCondition(dto);
    }
}
