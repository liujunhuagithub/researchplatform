package cn.edu.ncepu.researchplatform.security;

import cn.edu.ncepu.researchplatform.controller.OtherController;
import cn.edu.ncepu.researchplatform.utils.Utils;
import cn.hutool.core.util.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CustomizerPasseordEncoder extends BCryptPasswordEncoder {

    @Autowired
    @Qualifier("captcha")
    private Cache captchaCache;

    public CustomizerPasseordEncoder() {
        super(12);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        String username = Utils.getRequest().getParameter("username");
        String phoneCode = Utils.getRequest().getParameter(OtherController.PHONECODE_PARAM_NAME);
        System.out.println("----------"+captchaCache.get(username,String.class));
        if (PhoneUtil.isPhone(username) && StringUtils.hasText(phoneCode) && captchaCache.get(username,String.class).equals(phoneCode)) {
            return true;
        }
        return super.matches(rawPassword, encodedPassword);
    }
}
