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

    @GetMapping("/people/{username}/icon")
    public R 查询某人icon(@PathVariable String username) {
        return R.success(peopleService.findIcon(username));
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
