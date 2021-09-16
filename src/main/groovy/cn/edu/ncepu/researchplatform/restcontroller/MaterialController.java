package cn.edu.ncepu.researchplatform.restcontroller;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.entity.Area;
import cn.edu.ncepu.researchplatform.entity.Material;
import cn.edu.ncepu.researchplatform.entity.vo.MaterialVo;
import cn.edu.ncepu.researchplatform.service.AreaService;
import cn.edu.ncepu.researchplatform.service.MaterialService;
import cn.edu.ncepu.researchplatform.service.OtherService;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ObjectMapper om;
    @Value("${customize.save-location}")
    private String pathPre;

    @PostMapping("/material")
    public Integer 新增material参数peopleId不用写(Material material, MultipartFile materialFile) throws IOException {
        String uuid = UUID.randomUUID().toString();
        File saveFile = Paths.get(pathPre, "ResearchPlatformFiles", "material", uuid + ".temp").toFile();
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        materialFile.transferTo(saveFile);
        if (OtherService.isIllegalFile(saveFile)) {
            throw CustomException.SENSITIVE_ERROR_Exception;
        }
        material.setPeopleId(peopleService.findByUsername(Utils.getCurrent()).getId());
        material.setFlag(0);
        String path = uuid + "." + materialFile.getOriginalFilename().split("\\.")[1];
        material.setPath(path);
        Integer newId = materialService.insertMaterial(material);
        saveFile.renameTo(Paths.get(pathPre, "ResearchPlatformFiles", "material", path).toFile());
        return newId;
    }

    @DeleteMapping("/material/{materialId}")
    public boolean 删除material(@PathVariable Integer materialId) {
        Integer peopleId = peopleService.findByUsername(Utils.getCurrent()).getId();
        return materialService.deleteById(materialId, peopleId);
    }

    @GetMapping("/material/{materialId}")
    public MaterialVo 查询Id(@PathVariable Integer materialId) throws JsonProcessingException {
        Material material = materialService.findById(materialId);
        List<Area> areas = Arrays.stream(om.readValue(material.getAreaTemp(), Integer[].class)).map(i -> areaService.findAllArea().get(i)).collect(Collectors.toList());
        return new MaterialVo(material, areas, peopleService.findById(material.getPeopleId()));
    }

    @GetMapping("/poeple/{username}/material}")
    public List<Material> 查询某people的material(@PathVariable String username) throws JsonProcessingException {
        return materialService.findByPeopleId(peopleService.findByUsername(username).getId());
    }

    @GetMapping("/material/content/{materialId}")
    public void 查询文件ById(@PathVariable Integer materialId, HttpServletResponse response) throws JsonProcessingException {
        Material material = materialService.findById(materialId);
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
        ServletUtil.write(response, Paths.get(pathPre, "ResearchPlatformFiles", "material", material.getPath()).toFile());
    }
}
