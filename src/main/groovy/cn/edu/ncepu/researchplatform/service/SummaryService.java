package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Evaluate;
import cn.edu.ncepu.researchplatform.entity.Summary;
import cn.edu.ncepu.researchplatform.entity.dto.SummaryDto;
import cn.edu.ncepu.researchplatform.entity.vo.SummaryDetailVo;
import cn.edu.ncepu.researchplatform.entity.vo.SummaryPageVo;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.mapper.SummaryMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummaryService {
    @Autowired
    private SummaryMapper summaryMapper;
    @Autowired
    private PeopleMapper peopleMapper;

    public Summary findById(Integer summaryId) {
        return summaryMapper.findById(summaryId);
    }

    public SummaryPageVo findByCondition(SummaryDto dto) {
        List<Summary> summaries = summaryMapper.findByCondition(dto);
        Integer total = summaryMapper.findCountByCondition(dto);
        List<SummaryDetailVo> voList = summaries.stream()
                .map(summary -> new SummaryDetailVo(summary, peopleMapper.findById(summary.getPeopleId()), null))
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
