package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.service.AreaService;
import cn.edu.ncepu.researchplatform.service.ArticleService;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private PeopleService peopleService;

    @PostMapping("/area")
    public boolean 新增Area(@RequestBody Area area) {
        areaService.insertArea(area);
        return true;
    }

    @PutMapping("/area/{areaId}")
    @ApiOperation(value = "请求体json，只有一项 参数名name")
    public boolean 修改area_name(@RequestBody Map<String, String> params, @PathVariable Integer areaId) {
        return areaService.updateName(params.get("name"), areaId);
    }

    @PutMapping("/article/{articleId}")
    @ApiOperation(value = "请求体json，只有一项 参数名flag -1~1")
    public boolean 修改article_flag(@RequestBody Map<String, Integer> params, @PathVariable Integer articleId) {
        return articleService.updateFlag(params.get("flag"), articleId);
    }

    @PutMapping("/auth/{username}")
    @ApiOperation(value = "请求体json，只有一项 参数名auth  0-3")
    public boolean 修改people权限身份(@PathVariable String username, @RequestBody Map<String, Integer> params) {
        Integer people_id = peopleService.findByUsername(username).getId();
        return peopleService.updateAuthById(people_id, params.get("auth"));
    }
}
