package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private PeopleService peopleService;
    public boolean updateFlag(Integer flag,Integer articleId){
        return articleMapper.updateFlag(flag,articleId);
    }
    public boolean deleteByIdAuthorId(Integer id,String username){
        return articleMapper.deleteByIdAuthorId(id,peopleService.findByUsername(username).getId());
    }
}
