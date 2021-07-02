package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.common.exception.CustomExceptionType;
import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.entity.Evaluate;
import cn.edu.ncepu.researchplatform.entity.Summary;
import cn.edu.ncepu.researchplatform.entity.dto.SummaryDto;
import cn.edu.ncepu.researchplatform.entity.vo.SummaryDetailVo;
import cn.edu.ncepu.researchplatform.entity.vo.SummaryPageVo;
import cn.edu.ncepu.researchplatform.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
    private SummaryService summaryService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private ObjectMapper om;

    @PostMapping("/area")
    public boolean 新增Area(@RequestBody Area area) {
        areaService.insertArea(area);
        return true;
    }

    @PutMapping("/area/{areaId}")
    public boolean 修改area_name是否可用0可用1禁用(@RequestBody Map<String, Integer> params, @PathVariable Integer areaId) {
        Integer flag = params.get("flag");
        Assert.isTrue(flag.equals(1) || flag.equals(0), CustomExceptionType.INPUT_ERROE.message);
        return areaService.updateDisabled(flag, areaId);
    }

    @PutMapping("/article/{articleId}")
    public boolean 修改article_flag参数名flag为负一或一(@RequestBody Map<String, Integer> params, @PathVariable Integer articleId) {
        Integer flag = params.get("flag");
        Assert.isTrue(flag.equals(-1) || flag.equals(1), CustomExceptionType.INPUT_ERROE.message);
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
        Integer flag = params.get("flag");
        Assert.isTrue(flag.equals(-1) || flag.equals(1), CustomExceptionType.INPUT_ERROE.message);
        return materialService.throughBatchArea(materialId, flag);
    }

    @GetMapping("/summary/{summaryId}")
    public SummaryDetailVo 获取某个summary(@PathVariable Integer summaryId) throws JsonProcessingException {
        Summary summary = summaryService.findById(summaryId);
        TreeMap<Integer, Object> tempMap = om.readValue(summary.getContent(), TreeMap.class);
        for (Map.Entry<Integer, Object> entry : tempMap.entrySet()) {
            tempMap.put(entry.getKey(), evaluateService.findById((Integer) (entry.getValue())));
        }
        return new SummaryDetailVo(summary, peopleService.findById(summary.getPeopleId()), tempMap);
    }

    @GetMapping("/summary")
    public SummaryPageVo 获取summary(SummaryDto dto) {
        return summaryService.findByCondition(dto);
    }
}
