package cn.edu.ncepu.researchplatform.entity.vo;

import cn.edu.ncepu.researchplatform.entity.Evaluate;
import cn.edu.ncepu.researchplatform.entity.People;
import cn.edu.ncepu.researchplatform.entity.Summary;

import java.util.List;

public class SummaryDetailVo extends Summary {
    private Summary summary;
    private People people;
    private List<Evaluate> evaluates;

    public SummaryDetailVo(Summary summary, People people, List<Evaluate> evaluates) {
        this.summary = summary;
        this.people = people;
        this.evaluates = evaluates;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public List<Evaluate> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<Evaluate> evaluates) {
        this.evaluates = evaluates;
    }
}
