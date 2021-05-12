package cn.edu.ncepu.researchplatform.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

@Validated
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Star  {
    private Integer peopleId;
    private Integer evaluateId;

    /**
     * 0无操作1点赞-1反对
     */
    private Integer flag;


}