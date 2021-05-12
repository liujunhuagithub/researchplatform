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
}