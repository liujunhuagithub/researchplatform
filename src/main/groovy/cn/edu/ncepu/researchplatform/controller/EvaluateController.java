package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.common.R;
import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.entity.Evaluate;
import cn.edu.ncepu.researchplatform.entity.Summary;
import cn.edu.ncepu.researchplatform.entity.vo.EvaluateVo;
import cn.edu.ncepu.researchplatform.service.EvaluateService;
import cn.edu.ncepu.researchplatform.service.OtherService;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import groovy.util.Eval;
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
    @Autowired
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
    public List<EvaluateVo> 某article下所有evaluate(@PathVariable Integer articleId, Integer current, Integer size) {
        return evaluateService.findByArticleId(articleId, current, size).stream().map(e ->
                new EvaluateVo(e, peopleService.findById(e.getPeopleId()).getUsername())).collect(Collectors.toList());
    }

    @PostMapping("/discuss")
    @PostAuthorize("#evaluateService.isArticleContainArea(#evaluate.articleId)")
    public Integer 添加讨论(Evaluate evaluate) {
        if (OtherService.isIllegalEvaluate(evaluate.getContent())){
            throw CustomException.SENSITIVE_ERROR_Exception;
        }
        evaluate.setPeopleId(peopleService.findByUsername(Utils.getCurrent()).getId());
        return evaluateService.insertDiscuss(evaluate);
    }


    @GetMapping("/discuss/{parentId}")
    public List<EvaluateVo> 给定parentId获取其子discuss(@PathVariable Integer parentId, Integer current, Integer size) {
        return evaluateService.findDisscussByParentId(parentId, current, size).stream().map(e ->
                new EvaluateVo(e, peopleService.findById(e.getPeopleId()).getUsername())).collect(Collectors.toList());
    }

    @PostMapping("/summary")
    public boolean 新增summary(Evaluate[] summary) {
        Summary _s = new Summary();
        return evaluateService.insertBatchSummary(_s, summary);
    }
}
