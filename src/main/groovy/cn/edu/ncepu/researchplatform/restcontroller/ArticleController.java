package cn.edu.ncepu.researchplatform.restcontroller;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.common.exception.CustomExceptionType;
import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.entity.Article;
import cn.edu.ncepu.researchplatform.entity.dto.ArticleDto;
import cn.edu.ncepu.researchplatform.entity.vo.ArticleVo;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import cn.edu.ncepu.researchplatform.service.AreaService;
import cn.edu.ncepu.researchplatform.service.ArticleService;
import cn.edu.ncepu.researchplatform.service.OtherService;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private PeopleService peopleService;
    @Value("${customize.save-location}")
    private String pathPre;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ObjectMapper om;

    @PreAuthorize("#username== authentication.name or hasAuthority('admin')")
    @DeleteMapping("/people/{username}/article/{articleId}")
    public boolean 删除article路径参数username必须是文章作者而非当前登录用户(@PathVariable String username, @PathVariable Integer articleId) {
        return articleService.deleteByIdAuthorId(articleId, username);
    }

    @GetMapping("/article/{id}")
    public Article 查询某article(@PathVariable Integer id) {
        Article article = articleService.findArticleById(id);
        //   普通用户                  已删除                           有问题
        if (!Utils.isAdmin() && (article.getGmtDelete() != null || !article.getFlag().equals(1))) {
            return null;
        }
        return article;
    }

    @GetMapping("/article")
    public ArticleVo 条件查询(ArticleDto dto, String username) {
        PeopleDetails author = peopleService.findByUsername(username);
        if (author != null) {
            dto.setAuthorId(author.getId());
        }
        if (!(Utils.isAdmin() || Utils.isCurrent(username))) {
            dto.setFlag(1);
//已删除如何处理？
        }

        return articleService.findByCondition(dto);
    }

    @GetMapping("/article/personaliseEvaluate")
    public ArticleVo 多个article用于评价(ArticleDto dto) {
        PeopleDetails author = peopleService.findByUsername(Utils.getCurrent());
        dto.setAuthorId(author.getId());
        return articleService.personaliseEvaluate(dto);
    }

    @GetMapping("/article/content/{articleId}")
    public void 查询article正文(@PathVariable Integer articleId, HttpServletResponse response) {
        Article article = articleService.findArticleById(articleId);
        Assert.isTrue(Utils.isAdmin() || article.getGmtDelete() == null, CustomExceptionType.AUTH_ERROR.message);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        ServletUtil.write(response, Paths.get(pathPre, "ResearchPlatformFiles", "article", article.getPath()).toFile());
    }


    @PostMapping("/article")
    @Transactional(rollbackFor = Exception.class)
    public Integer 新增article(Article article, String areaTemp, MultipartFile articleFile) throws IOException {
        List<Area> areas = Arrays.stream(om.readValue(areaTemp, Integer[].class)).map(i -> areaService.findAllArea().get(i)).collect(Collectors.toList());

        if (!articleService.isPeopleContainArea(areas)){
            throw CustomException.AUTH_ERROR_Exception;
        }
        String uuid = UUID.randomUUID().toString();
        File saveFile = Paths.get(pathPre, "ResearchPlatformFiles", "article", uuid + ".temp").toFile();
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        articleFile.transferTo(saveFile);
        if (OtherService.isIllegalFile(saveFile)) {
            throw CustomException.SENSITIVE_ERROR_Exception;
        }
        article.setAreas(areas);
        article.setAuthorId(peopleService.findByUsername(Utils.getCurrent()).getId());
        String path = uuid + "." + articleFile.getOriginalFilename().split("\\.")[1];
        article.setPath(path);
        Integer newId = articleService.insert(article);
        File n = Paths.get(pathPre, "ResearchPlatformFiles", "article", path).toFile();
        saveFile.renameTo(n);
        return newId;
    }

    @PutMapping("/article/{articleId}/report")
    public boolean 举报article(@PathVariable Integer articleId) {
        return articleService.updateFlag(-2, articleId);
    }
}
