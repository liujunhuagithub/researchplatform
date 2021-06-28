package cn.edu.ncepu.researchplatform.entity.vo;

import cn.edu.ncepu.researchplatform.entity.Summary;

import java.util.List;

public class SummaryPageVo {
    private Integer total;
    private Integer current;
    private Integer size;
    private List<SummaryDetailVo> summaries;

    public SummaryPageVo(Integer total, Integer current, Integer size, List<SummaryDetailVo> summaries) {
        this.total = total;
        this.current = current;
        this.size = size;
        this.summaries = summaries;
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

    public List<SummaryDetailVo> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<SummaryDetailVo> summaries) {
        this.summaries = summaries;
    }
}
