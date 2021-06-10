package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Article;
import cn.edu.ncepu.researchplatform.entity.Evaluate;
import cn.edu.ncepu.researchplatform.entity.People;
import cn.edu.ncepu.researchplatform.mapper.AreaMapper;
import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankService {
    @Value("${rank.size}")
    private Integer rankSize;
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private PeopleMapper peopleMapper;
    public List<People> peoples() {

    }

    public List<Evaluate> evaluate() {

    }

    public List<Article> evaluate() {

    }

    public List<People> author() {

    }
}
