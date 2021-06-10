package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.entity.People;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeopleController {
    @Autowired
    private PeopleService peopleService;

    @GetMapping("/people/{id}")
    @ApiOperation(value = "id而非username")
    public People 查询某个people(@PathVariable Integer id) {
        People people = peopleService.findAllInfo(id);
        if (!Utils.isAdmin() && "black".equals(people.getAuth())) {
            return null;
        }
        return people;
    }

    @GetMapping("/people/{id}/icon")
    public String 查询某人icon(@PathVariable Integer id) {
        return peopleService.findIcon(id);
    }
}
