package cn.edu.ncepu.researchplatform.entity.vo;

import cn.edu.ncepu.researchplatform.entity.People;

import java.util.List;

public class PeopleVo extends People {
    private Integer total;
    private Integer current;
    private Integer size;
    private List<People> peoples;

    public PeopleVo(Integer total, Integer current, Integer size, List<People> peoples) {
        this.total = total;
        this.current = current;
        this.size = size;
        this.peoples = peoples;
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

    public List<People> getPeoples() {
        return peoples;
    }

    public void setPeoples(List<People> peoples) {
        this.peoples = peoples;
    }
}
