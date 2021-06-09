package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeopleController {
    @Autowired
    private PeopleService peopleService;
}
