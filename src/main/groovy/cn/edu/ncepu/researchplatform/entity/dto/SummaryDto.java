package cn.edu.ncepu.researchplatform.entity.dto;

import cn.edu.ncepu.researchplatform.entity.Summary;

import java.time.LocalDateTime;

public class SummaryDto extends Summary {
    private LocalDateTime lc;
    private LocalDateTime rc;
    private Integer current = 1;
    private Integer size = 25;
    private String username;
    public LocalDateTime getLc() {
        return lc;
    }

    public void setLc(LocalDateTime lc) {
        this.lc = lc;
    }

    public LocalDateTime getRc() {
        return rc;
    }

    public void setRc(LocalDateTime rc) {
        this.rc = rc;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
