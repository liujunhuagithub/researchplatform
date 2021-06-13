package cn.edu.ncepu.researchplatform.entity.vo;

import cn.edu.ncepu.researchplatform.entity.Article;

public class ArticleVo {
    private Article article;
    private String username;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArticleVo(Article article, String username) {
        article.setAuthorId(null);
        this.article = article;
        this.username = username;
    }
}
