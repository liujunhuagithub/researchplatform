package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.service.EvaluateService;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class EvaluateController {
    @Autowired
    private EvaluateService evaluateService;

    //    @PostAuthorize("#username==#authentication.name or hasAuthority('admin')")
    @DeleteMapping("/people/{username}/evaluate/{evaluateId}")
    public boolean 删除evaluate(@PathVariable String username, @PathVariable Integer evaluateId) {
        return evaluateService.deleteByIdPeopleId(evaluateId, username);
    }

    @PutMapping("/evaluate/{id}/adopt")
    public boolean 置顶采纳evaluate(@PathVariable Integer id, @RequestBody Map<String, Integer> params) {
        Integer flag = params.get("flag");
        if (flag.equals(-1)) throw CustomException.INPUT_ERROE_Exception;
        if (!evaluateService.idAuthorAdpot(id)) throw CustomException.AUTH_ERROR_Exception;
        return evaluateService.updateFlag(id, params.get("flag"));
    }
}
