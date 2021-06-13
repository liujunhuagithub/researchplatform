package cn.edu.ncepu.researchplatform.entity.vo;

import cn.edu.ncepu.researchplatform.entity.Evaluate;

public class EvaluateVo  {
    private Evaluate evaluate;
    private String username;

    public EvaluateVo(Evaluate evaluate, String username) {
        this.evaluate = evaluate;
        this.username = username;
    }

    public Evaluate getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
