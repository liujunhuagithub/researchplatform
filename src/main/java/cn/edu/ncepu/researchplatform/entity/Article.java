package cn.edu.ncepu.researchplatform.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Article {
    private Integer id;
    private Integer peopleId;
    private String title;
    private String ref;
    private String content;
    private Double weight;
    private Double score;
    private List<Area> areas;
    /**
     * 1正常-1违禁0审核中
     */
    private Integer flag;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtDelete;

}