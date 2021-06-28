package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.entity.Article;
import cn.edu.ncepu.researchplatform.entity.dto.ArticleDto;
import cn.edu.ncepu.researchplatform.entity.vo.ArticleVo;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
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
    @Autowired
    @Lazy
    private AreaService areaService;

    public boolean updateFlag(Integer flag, Integer articleId) {
        return articleMapper.updateFlag(flag, articleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIdAuthorId(Integer articleId, String username) {
        return articleMapper.deleteByIdAuthorId(articleId, peopleService.findByUsername(username).getId());
    }

    @Cacheable(value = "article", key = "#articleId")
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Article findArticleById(Integer articleId) {
        return articleMapper.findById(articleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(Article article) {
        article.getAreas().forEach(a -> articleMapper.insertArea(article.getId(), a.getId()));
        return articleMapper.insert(article);
    }

    public ArticleVo findByCondition(ArticleDto dto) {
        List<Article> articles = articleMapper.findByCondition(dto);
        Integer total = articleMapper.findCountByCondition(dto);
        return new ArticleVo(total,dto.getCurrent(),dto.getSize(),articles);
    }

    public boolean isPeopleContainArea(List<Area> intentAreas) {
        return areaService.isAreaContain(areaMapper.findPeopleAreas(peopleService.findByUsername(Utils.getCurrent()).getId()), intentAreas);
    }
}
