package cn.edu.ncepu.researchplatform.controller;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.service.OtherService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
public class OtherController {

    public static String CAPTCH_HEADER_NAME = "captchakey";
    public static String CAPTCHCODE_PARAM_KEY_NAME = "captchakey";
    public static String CAPTCHCODE_PARAM_NAME = "captchaCode";
    public static String PHONECODE_PARAM_NAME = "phoneCode";
    @Autowired
    @Qualifier("captcha")
    private Cache captchaCache;

    @Autowired
    OtherService otherService;

    @GetMapping("/captchaCode")
    public void 图形验证码(HttpServletResponse response) throws IOException {
        String key = UUID.randomUUID().toString();
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);
        captchaCache.put(key, captcha.getCode());
        response.setContentType("image/png");
        response.addHeader(CAPTCH_HEADER_NAME, key);
        captcha.write(response.getOutputStream());
    }

    @GetMapping("/identityCaptchaCode")
    public boolean 校验图形验证码(@RequestBody Map<String, String> params) {
        String captchaKey = params.get(CAPTCHCODE_PARAM_KEY_NAME);
        String code = captchaCache.get(captchaKey,String.class);
        if (!params.get(OtherController.CAPTCHCODE_PARAM_NAME).equals(code)) {
            throw CustomException.INPUT_ERROE_Exception;
        }
        return true;
    }

    @PostMapping("/phoneCode")
    public boolean 手机验证码(@RequestParam String phone ) throws IOException {
        captchaCache.put(phone, otherService.phoneCode(phone));
        return true;
    }
}
