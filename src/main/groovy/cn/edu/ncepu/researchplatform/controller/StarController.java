package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.service.StarService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StarController {
    @Autowired
    private StarService starService;

    @PostMapping("/star/{evaluateId}")
    @PostAuthorize("#starService.isEqualArea(#evaluateId)==true")
    @ApiOperation(value = "请求体json，只有一项 参数名flag -1 0 1")
    public boolean saveStar(@PathVariable Integer evaluateId, @RequestBody Map<String, Integer> params) {
        boolean r = starService.saveStar(evaluateId, params.get("flag"));
        starService.notifyAuthorStar(evaluateId);
        return r;
    }
}
