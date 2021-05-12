package cn.edu.ncepu.researchplatform.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDateTime;
@Validated
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Summary {

    private Integer id;
    /**
     * 排序的article_id顺序
     */
    private String content;
    private Integer peopleId;
    private LocalDateTime gmtCreate;

}