package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.entity.Evaluate;
import cn.edu.ncepu.researchplatform.entity.Summary;
import cn.edu.ncepu.researchplatform.entity.vo.EvaluatePageVo;
import cn.edu.ncepu.researchplatform.entity.vo.EvaluateVo;
import cn.edu.ncepu.researchplatform.service.EvaluateService;
import cn.edu.ncepu.researchplatform.service.OtherService;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
public class EvaluateController {
    @Autowired
    private EvaluateService evaluateService;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private ObjectMapper om;

    @PreAuthorize("#username==authentication.name or hasAuthority('admin')")
    @DeleteMapping("/people/{username}/evaluate/{evaluateId}")
    public boolean 删除evaluate路径参数username必须是evaluate发表者而非当前登录用户(@PathVariable String username, @PathVariable Integer evaluateId) {
        return evaluateService.deleteById(evaluateId, peopleService.findByUsername(username).getId());
    }

    @PutMapping("/evaluate/{evaluateId}/adopt")
    @Transactional(rollbackFor = Exception.class)
    public boolean 置顶或取消evaluate(@PathVariable Integer evaluateId, @RequestBody Map<String, Integer> params) {
        Integer flag = params.get("flag");
        switch (flag) {
            case 1:
                peopleService.costForEvaluate(evaluateId, 10);
            case 0:
                break;
            default:
                throw CustomException.INPUT_ERROE_Exception;
        }
        if (!evaluateService.getAuthor(evaluateId).getUsername().equals(Utils.getCurrent())) {
            throw CustomException.AUTH_ERROR_Exception;
        }
        return evaluateService.updateFlag(evaluateId, params.get("flag"));
    }

    @GetMapping("/article/{articleId}/evaluate")
    public List<EvaluateVo> 某article下所有evaluate(@PathVariable Integer articleId, @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "25") Integer size) {
        //当前用户为作者要显示niming的
        return evaluateService.findByArticleId(articleId, current, size).stream().map(e ->
                new EvaluateVo(e, peopleService.findById(e.getPeopleId()).getUsername())).collect(Collectors.toList());
    }

    @PostMapping("/discuss")
    @PreAuthorize("#evaluateService.isArticleContainArea(#evaluate.articleId)")
    public Integer 添加讨论(@RequestBody Evaluate evaluate) {
        if (OtherService.isIllegalEvaluate(evaluate.getContent())) {
            throw CustomException.SENSITIVE_ERROR_Exception;
        }
        evaluate.setPeopleId(peopleService.findByUsername(Utils.getCurrent()).getId());
        return evaluateService.insertDiscuss(evaluate);
    }


    @GetMapping("/people/{username}/evaluate")
    public EvaluatePageVo 获取某people发表的所有evaluate(@PathVariable String username, @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "25") Integer size) {
        return evaluateService.findByPeopleId(peopleService.findByUsername(username).getId(), current, size);
    }

    @GetMapping("/discuss/{parentId}")
    public List<EvaluateVo> 给定parentId获取其子discuss(@PathVariable Integer parentId, @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "25") Integer size) {
        return evaluateService.findDisscussByParentId(parentId, current, size).stream().map(e ->
                new EvaluateVo(e, peopleService.findById(e.getPeopleId()).getUsername())).collect(Collectors.toList());
    }

    @PostMapping("/summary")
    public boolean 新增summary(@RequestBody Map<Integer, Evaluate> evaluates) throws JsonProcessingException {
        Summary _s = new Summary();
        TreeMap<Integer, Integer> tempMap = new TreeMap<>(Integer::compareTo);
        for (Map.Entry<Integer, Evaluate> entry : evaluates.entrySet()) {
            Integer key = entry.getKey();
            tempMap.put(key, evaluates.get(key).getId());
        }

        String content = om.writeValueAsString(tempMap);
        _s.setPeopleId(peopleService.findByUsername(Utils.getCurrent()).getId());
        _s.setContent(content);
        return evaluateService.insertBatchSummary(_s, evaluates.values());
    }

    @PutMapping("/evaluate/{evaluateId}/report")
    public boolean 举报evaluate(@PathVariable Integer evaluateId) {
        return evaluateService.updateFlag(evaluateId, -2);
    }

}
