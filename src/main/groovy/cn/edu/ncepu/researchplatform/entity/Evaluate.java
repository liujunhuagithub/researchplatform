package cn.edu.ncepu.researchplatform.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDateTime;

@Validated
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Evaluate implements Serializable {

    private Integer id;
    private Integer peopleId;
    private Integer articleId;
    private String content;
    private Double weight;
    private Double scoreItem;
    private Integer star;
    private Integer oppose;

    /**
     * 0不置顶1置顶-1违禁
     */
    private Integer flag;
    private Integer summaryId;

    /**
     * 0父评价
     */
    private Integer parentId;
    /**
     * 默认公开0
     */
    private Boolean niming;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(Integer peopleId) {
        this.peopleId = peopleId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getScoreItem() {
        return scoreItem;
    }

    public void setScoreItem(Double scoreItem) {
        this.scoreItem = scoreItem;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getOppose() {
        return oppose;
    }

    public void setOppose(Integer oppose) {
        this.oppose = oppose;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(Integer summaryId) {
        this.summaryId = summaryId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getNiming() {
        return niming;
    }

    public void setNiming(Boolean niming) {
        this.niming = niming;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtDelete() {
        return gmtDelete;
    }

    public void setGmtDelete(LocalDateTime gmtDelete) {
        this.gmtDelete = gmtDelete;
    }
}