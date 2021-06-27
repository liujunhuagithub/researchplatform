package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.entity.Summary;
import cn.edu.ncepu.researchplatform.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public boolean 修改article_flag参数名flag为负一或一(@RequestBody Map<String, Integer> params, @PathVariable Integer articleId) {
        Integer flag = params.get("flag");
        if (!(flag.equals(-1)||flag.equals(1))){
            throw CustomException.INPUT_ERROE_Exception;
        }
        return articleService.updateFlag(flag, articleId);
    }

    @PutMapping("/auth/{username}")
    public boolean 修改people权限身份参数名auth为负一_3(@PathVariable String username, @RequestBody Map<String, Integer> params) {
        Integer peopleId = peopleService.findByUsername(username).getId();
        return peopleService.updateAuthById(peopleId, params.get("auth"));
    }

    @PutMapping("/evaluate/{id}/illegal")
    public boolean 封杀evaluate无需参数(@PathVariable Integer id) {
        return evaluateService.updateFlag(id, -1);
    }


    @PutMapping("/material/{materialId}/flag")
    public boolean 全通过1或全部不通过负一(@PathVariable Integer materialId, @RequestBody Map<String, Integer> params) throws JsonProcessingException {
        return materialService.throughBatchArea(materialId, params.get("flag"));
    }

    @GetMapping("/summary/{summaryId}")
    public Summary 获取某个summary() {
        return null;
    }

    @GetMapping("/summary")
    public Summary 获取summary() {
        return null;
    }
}
