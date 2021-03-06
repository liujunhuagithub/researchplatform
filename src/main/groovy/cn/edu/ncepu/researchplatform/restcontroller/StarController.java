package cn.edu.ncepu.researchplatform.restcontroller;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.service.EvaluateService;
import cn.edu.ncepu.researchplatform.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class StarController {
    @Autowired
    private StarService starService;
    @Autowired
    private EvaluateService evaluateService;

    @PostMapping("/star/{evaluateId}")
//    @PreAuthorize("#starService.isContainArea(#evaluateId)")
    public boolean 点赞反对取消tar(@PathVariable Integer evaluateId, @RequestBody Map<String, Integer> params) {
        if (!starService.isContainArea(evaluateId)) {
            throw CustomException.AREA_ERROR_Exception;
        }
        boolean r = starService.saveStar(evaluateId, params.get("flag"));
        starService.notifyAuthorStar(evaluateId);
        return r;
    }

    @GetMapping("/people/{username}/article/{articleId}/star")
    public List<Integer> 某人在某article下对那些Evaluate点赞(@PathVariable Integer articleId, @PathVariable String username) {
        return evaluateService.findBypeopleToArticle(articleId, username);
    }
}
