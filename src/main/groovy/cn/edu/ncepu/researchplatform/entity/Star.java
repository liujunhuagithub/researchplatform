package cn.edu.ncepu.researchplatform.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Validated
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Star {
    private Integer peopleId;
    private Integer evaluateId;

    /**
     * 0无操作1点赞-1反对
     */
    private Integer flag;
    private LocalDateTime gmtCreate;

    public Integer getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(Integer peopleId) {
        this.peopleId = peopleId;
    }

    public Integer getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Integer evaluateId) {
        this.evaluateId = evaluateId;
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
}