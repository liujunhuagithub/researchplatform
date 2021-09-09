package cn.edu.ncepu.researchplatform.entity.dto;

import cn.edu.ncepu.researchplatform.entity.Evaluate;

import java.time.LocalDateTime;

public class EvaluateDto extends Evaluate {
    private LocalDateTime lc;
    private LocalDateTime rc;
    private LocalDateTime ld;
    private LocalDateTime rd;
    private Integer current = 1;
    private Integer size = 25;

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

    public LocalDateTime getLd() {
        return ld;
    }

    public void setLd(LocalDateTime ld) {
        this.ld = ld;
    }

    public LocalDateTime getRd() {
        return rd;
    }

    public void setRd(LocalDateTime rd) {
        this.rd = rd;
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
