package cn.edu.ncepu.researchplatform.entity.vo;

import cn.edu.ncepu.researchplatform.entity.Article;

import java.util.List;

public class ArticleVo {
    private Integer total;
    private Integer current;
    private Integer size;
    private List<Article> articles;

    public ArticleVo(Integer total, Integer current, Integer size, List<Article> articles) {
        this.total = total;
        this.current = current;
        this.size = size;
        this.articles = articles;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
