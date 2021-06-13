package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.entity.vo.EvaluateVo;
import cn.edu.ncepu.researchplatform.service.EvaluateService;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EvaluateController {
    @Autowired
    private EvaluateService evaluateService;
    private PeopleService peopleService;

    @PostAuthorize("#username==#authentication.name or hasAuthority('admin')")
    @DeleteMapping("/people/{username}/evaluate/{evaluateId}")
    public boolean 删除evaluate(@PathVariable String username, @PathVariable Integer evaluateId) {
        return evaluateService.deleteByIdPeopleId(evaluateId, username);
    }

    @PutMapping("/evaluate/{id}/adopt")
    @Transactional(rollbackFor = Exception.class)
    public boolean 置顶采纳evaluate(@PathVariable Integer id, @RequestBody Map<String, Integer> params) {
        Integer flag = params.get("flag");
        if (flag.equals(-1)) throw CustomException.INPUT_ERROE_Exception;
        if (!evaluateService.getAuthor(id).getUsername().equals(Utils.getCurrent()))
            throw CustomException.AUTH_ERROR_Exception;
        peopleService.cost(id, 10);
        return evaluateService.updateFlag(id, params.get("flag"));
    }

    @GetMapping("/article/{articleId}/evaluate")
    public List<EvaluateVo> 某article下所有evaluate(@PathVariable Integer articleId) {
        return evaluateService.findByArticleId(articleId).stream().map(e ->
                new EvaluateVo(e, peopleService.findById(e.getPeopleId()).getUsername())).collect(Collectors.toList());
    }
}
