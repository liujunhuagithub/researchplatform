package cn.edu.ncepu.researchplatform.viewcontroller;

import cn.edu.ncepu.researchplatform.service.ArticleService;
import cn.edu.ncepu.researchplatform.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CatController {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "/article/cat/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String catAContent(@PathVariable Integer id, ModelMap modelMap) {
        modelMap.addAttribute("contentUrl", "/cat/article/" + articleService.findArticleById(id).getPath());
        return "catarticle";
    }

    @GetMapping(value = "/material/cat/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String catMContent(@PathVariable Integer id, ModelMap modelMap) {
        modelMap.addAttribute("contentUrl", "/cat/material/" + materialService.findById(id).getPath());
        return "catmaterial";
    }
}
