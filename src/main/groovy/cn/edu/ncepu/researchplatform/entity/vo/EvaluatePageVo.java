package cn.edu.ncepu.researchplatform.entity.vo;

import cn.edu.ncepu.researchplatform.entity.Evaluate;

import java.util.List;

public class EvaluatePageVo {
    private Integer total;
    private Integer current;
    private Integer size;
    private List<Evaluate> evaluates;

    public EvaluatePageVo(Integer total, Integer current, Integer size, List<Evaluate> evaluates) {
        this.total = total;
        this.current = current;
        this.size = size;
        this.evaluates = evaluates;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

    public List<Evaluate> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<Evaluate> evaluates) {
        this.evaluates = evaluates;
    }
}
