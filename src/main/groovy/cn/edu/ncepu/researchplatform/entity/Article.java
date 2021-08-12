package cn.edu.ncepu.researchplatform.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Validated
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Article {
    private Integer id;
    private Integer authorId;
    private People author;
    private String title;
    private String ref;
    private String path;
    private Double weight;
    private Double score;
    private List<Area> areas;
    /**
     * 1正常-1违禁0审核中
     */
    private Integer flag;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
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

    public People getAuthor() {
        return author;
    }

    public void setAuthor(People author) {
        this.author = author;
    }
}