package cn.edu.ncepu.researchplatform.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.http.ContentType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class PeopleController {
    @GetMapping("/verfyCode")
    public void captcha(HttpSession session, HttpServletResponse response) throws IOException {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);
        session.setAttribute("verfyCode", captcha);
        response.setContentType("image/png");
        captcha.write(response.getOutputStream());
    }
}
