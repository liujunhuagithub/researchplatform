package cn.edu.ncepu.researchplatform.security;

import cn.edu.ncepu.researchplatform.utils.Utils;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CustomizerProvider extends DaoAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        System.out.println("------执行额外验证逻辑");
        HttpSession session = Utils.getRequest().getSession();
        LineCaptcha captcha = (LineCaptcha) (session.getAttribute(Utils.verfyCode));
//        if (!captcha.verify(Utils.getRequest().getParameter(Utils.verfyCode))) {
//            session.invalidate();
//            throw new BadCredentialsException("验证码错误");
//        }
        session.invalidate();
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
