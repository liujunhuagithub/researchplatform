package cn.edu.ncepu.researchplatform.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDateTime;

@Validated
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Material {

    private Integer id;
    private Integer peopleId;
    private String content;
    private String area;

    /**
     * 0正在1通过-1失败
     */
    private Integer flag;
    private LocalDateTime gmtCreate;
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

}