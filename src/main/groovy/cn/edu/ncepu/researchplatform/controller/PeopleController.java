package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.common.R;
import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.entity.People;
import cn.edu.ncepu.researchplatform.entity.dto.PeopleDto;
import cn.edu.ncepu.researchplatform.entity.vo.PeopleVo;
import cn.edu.ncepu.researchplatform.security.CustomizerPasseordEncoder;
import cn.edu.ncepu.researchplatform.service.OtherService;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class PeopleController {
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private OtherService otherService;
    @Autowired
    private CustomizerPasseordEncoder customizerPasseordEncoder;

    @GetMapping("/people/{username}")
    public People 查询某个people(@PathVariable String username) {
        People people = peopleService.findAllInfo(username);
        if (!Utils.isAdmin() && "black".equals(people.getAuth())) {
            return null;
        }
        if (!(Utils.isAdmin() || Utils.isCurrent(username))) {
            people.hideInfo();
        }
        return people;
    }

    @GetMapping(value = "/people/{username}/icon",produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE})
    public byte[] 查询某人icon(@PathVariable String username) {
        String icon = peopleService.findIcon(username);
        return Base64Utils.decodeFromString(icon!=null?icon:"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");
    }

    @GetMapping("/people")
    public PeopleVo 条件查询(PeopleDto dto) {
        if (!Utils.isAdmin()) {
            dto.setIdCard(null);
            dto.setRealname(null);
            dto.setAuth(null);
            dto.setPhone(null);
        }
        return peopleService.findByCondition(dto);
    }

    @PutMapping("/password")
    public boolean 修改密码(String phone, String phoneCode, String newPass) {
        if (!otherService.verfyPhoneCode(phone, phoneCode)) {
            throw CustomException.INPUT_ERROE_Exception;
        }
        return peopleService.updatePass(phone, customizerPasseordEncoder.encode(newPass));
    }

    @PostMapping("/register")
    public Integer 注册people(People people, HttpServletRequest request) {
        if (!otherService.verfyPhoneCode(people.getPhone(), request.getParameter(OtherController.PHONECODE_PARAM_NAME))) {
            throw CustomException.INPUT_ERROE_Exception;
        }
        return peopleService.insertPeople(people);
    }

    @PutMapping("/people/{username}/phone")
    @PreAuthorize("#username==authentication.name or hasAuthority('admin')")
    public boolean 修改phone(String phone, String phoneCode, @PathVariable String username) {
        if (!otherService.verfyPhoneCode(phone, phoneCode)) {
            throw CustomException.INPUT_ERROE_Exception;
        }
        return peopleService.updatePhone(phone, username);
    }

    @PutMapping("/poeple/{username}/info")
    @PreAuthorize("#username==authentication.name or hasAuthority('admin')")
    public boolean 修改Info(@RequestBody People people, @PathVariable String username) {
        people.setId(peopleService.findByUsername(username).getId());
        return peopleService.updateInfo(people);
    }

    @PutMapping("/poeple/{username}/realname")
    @PreAuthorize("#username==authentication.name or hasAuthority('admin')")
    public boolean 实名认证(String idCard, String realname, @PathVariable String username) {
        if (!OtherService.isReadName(idCard, realname)) {
            throw CustomException.INPUT_ERROE_Exception;
        }
        return peopleService.updateReal(realname, idCard, username);
    }

    @PutMapping("/people/{username}/icon")
    @PreAuthorize("#username==authentication.name or hasAuthority('admin')")
    public boolean 修改icon(@PathVariable String username, MultipartFile iconFile) throws IOException {
        return peopleService.updateIcon(username, Base64Utils.encodeToString(iconFile.getBytes()) );
    }
}
