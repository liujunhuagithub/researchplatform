package cn.edu.ncepu.researchplatform.restcontroller;

import cn.edu.ncepu.researchplatform.entity.Article;
import cn.edu.ncepu.researchplatform.entity.Evaluate;
import cn.edu.ncepu.researchplatform.entity.People;
import cn.edu.ncepu.researchplatform.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rank")
public class RankController {
    @Value("${customize.rank-size}")
    private Integer rankSize;
    @Autowired
    private RankService rankService;

    @GetMapping("/peoples")
    public List<People> peoples() {
        List<People> peoples = rankService.peoples();
        return peoples.stream().limit(rankSize).collect(Collectors.toList());
    }

    @GetMapping("/evaluate")
    public List<Evaluate> evaluate() {
        List<Evaluate> evaluate = rankService.evaluate();
        return evaluate.stream().limit(rankSize).collect(Collectors.toList());
    }

    @GetMapping("/article")
    public List<Article> article() {
        List<Article> article = rankService.article();
        return article.stream().limit(rankSize).collect(Collectors.toList());

    }

    @GetMapping("/author")
    public List<People> author() {
        List<People> author = rankService.author();
        return author.stream().limit(rankSize).collect(Collectors.toList());

    }
}
