package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Evaluate;
import cn.edu.ncepu.researchplatform.entity.Summary;
import cn.edu.ncepu.researchplatform.entity.dto.SummaryDto;
import cn.edu.ncepu.researchplatform.entity.vo.SummaryDetailVo;
import cn.edu.ncepu.researchplatform.entity.vo.SummaryPageVo;
import cn.edu.ncepu.researchplatform.mapper.ArticleMapper;
import cn.edu.ncepu.researchplatform.mapper.EvaluateMapper;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.mapper.SummaryMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class SummaryService {
    @Autowired
    private SummaryMapper summaryMapper;
    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private EvaluateMapper evaluateMapper;
    @Autowired
    private ArticleMapper articleMapper;
    public Summary findById(Integer summaryId) {
        return summaryMapper.findById(summaryId);
    }

    public SummaryPageVo findByCondition(SummaryDto dto) {
        List<Summary> summaries = summaryMapper.findByCondition(dto);
        Integer total = summaryMapper.findCountByCondition(dto);
        List<SummaryDetailVo> voList = summaries.stream()
                .map(summary -> {
                    TreeMap tempMap = null;
                    try {
                        tempMap = om.readValue(summary.getContent(), TreeMap.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    Map<Integer, Object> rMap = new HashMap();
                    tempMap.forEach((k, v) -> {
                        Evaluate evaluate = evaluateMapper.findById(Integer.parseInt(v.toString()));
                        evaluate.setContent(evaluate.getContent()+OtherService.sp+articleMapper.findById(evaluate.getArticleId()).getTitle());
                        rMap.put(Integer.parseInt(k.toString()), evaluate);
                            }
                    );

                    return new SummaryDetailVo(summary, peopleMapper.findById(summary.getPeopleId()), rMap);
                })
                .collect(Collectors.toList());
        return new SummaryPageVo(total, dto.getCurrent(), dto.getSize(), voList);
    }

    public boolean updateEvaluateSummaryId(Evaluate evaluate) {
        return summaryMapper.updateEvaluateSummaryId(evaluate);
    }

    public Integer insert(Summary summary) {
        return summaryMapper.insert(summary);
    }
}
