package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.entity.People;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.IdcardUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeopleController {
    @Autowired
    private PeopleService peopleService;

    @GetMapping("/people/{username}")
    public People 查询某个people(@PathVariable String username) {
        People people = peopleService.findAllInfo(username);
        if (!Utils.isAdmin() && "black".equals(people.getAuth())) {
            return null;
        }
        if (!(Utils.isAdmin()||Utils.isCurrent(username+""))){
            people.hideInfo();
        }
        return people;
    }

    @GetMapping("/people/{username}/icon")
    public String 查询某人icon(@PathVariable String username) {
        return peopleService.findIcon(username);
    }
}
