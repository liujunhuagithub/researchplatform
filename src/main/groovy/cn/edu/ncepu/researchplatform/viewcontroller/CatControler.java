package cn.edu.ncepu.researchplatform.viewcontroller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CatControler {
    @GetMapping(value= "/article/cat/{id}",produces = MediaType.TEXT_HTML_VALUE)
    public String catContent(@PathVariable String id, ModelMap modelMap){
        modelMap.addAttribute("contentUrl","/article/content/"+id);
        return "cat";
    }
}
