package cn.edu.ncepu.researchplatform.entity.vo;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.entity.Material;
import cn.edu.ncepu.researchplatform.entity.People;

import java.util.List;

public class MaterialVo extends Material {

    private List<Area> areas;
    private People people;

    public MaterialVo(Material material,List<Area> areas, People people) {
        setArea(material.getArea());
        setFlag(material.getFlag());
        setGmtCreate(material.getGmtCreate());
        setContent(material.getContent());
        setId(material.getId());
        this.areas = areas;
        this.people = people;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}
