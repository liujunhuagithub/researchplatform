package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    @Autowired
    private EvaluateService evaluateService;

    @Autowired
    private MaterialService materialService;

    @PostMapping("/area")
    public boolean 新增Area(@RequestBody Area area) {
        areaService.insertArea(area);
        return true;
    }

    @PutMapping("/area/{areaId}")
    public boolean 修改area_name参数名name(@RequestBody Map<String, String> params, @PathVariable Integer areaId) {
        return areaService.updateName(params.get("name"), areaId);
    }

    @PutMapping("/article/{articleId}")
    public boolean 修改article_flag参数名flag为负一到一(@RequestBody Map<String, Integer> params, @PathVariable Integer articleId) {
        return articleService.updateFlag(params.get("flag"), articleId);
    }

    @PutMapping("/auth/{username}")
    public boolean 修改people权限身份参数名auth为0_3(@PathVariable String username, @RequestBody Map<String, Integer> params) {
        Integer people_id = peopleService.findByUsername(username).getId();
        return peopleService.updateAuthById(people_id, params.get("auth"));
    }

    @PutMapping("/evaluate/{id}/illegal")
    public boolean 封杀evaluate参数名flag只能是负一(@PathVariable Integer id, @RequestBody Map<String, Integer> params) {
        Integer flag = params.get("flag");
        if (!flag.equals(-1)) {
            throw CustomException.INPUT_ERROE_Exception;
        }
        return evaluateService.updateFlag(id, params.get("flag"));
    }


    @PutMapping("/material/{materialId}/flag")
    public boolean 全通过1或全部不通过负一(Integer materialId, boolean flag) throws JsonProcessingException {

        return materialService.throughBatchArea(materialId, flag ?1:-1);
    }
}
