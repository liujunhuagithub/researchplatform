package cn.edu.ncepu.researchplatform.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

@Validated
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Area {
    private Integer id;
    private String name;
    private Integer parentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
