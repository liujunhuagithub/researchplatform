package cn.edu.ncepu.researchplatform.entity.vo;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.entity.Material;
import cn.edu.ncepu.researchplatform.entity.People;

import java.util.List;

public class MaterialVo extends Material {

    private List<Area> areas;
    private People people;
    private String status;

    public MaterialVo(Material material, List<Area> areas, People people) {
//        setArea(material.getArea());
        switch (material.getFlag()) {
            case 0:
                setStatus("未审核");
                break;
            case -1:
                setStatus("认证失败");
                break;
            case 1:
                setStatus("已通过");
                break;
        }
        setFlag(material.getFlag());
        setGmtCreate(material.getGmtCreate());
        setId(material.getId());
        people.setPhone(null);
        this.areas = areas;
        this.people = people;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }
}
