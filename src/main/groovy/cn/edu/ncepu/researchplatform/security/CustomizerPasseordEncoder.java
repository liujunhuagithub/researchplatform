package cn.edu.ncepu.researchplatform.security;

import cn.edu.ncepu.researchplatform.controller.OtherController;
import cn.edu.ncepu.researchplatform.service.OtherService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import cn.hutool.core.util.PhoneUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CustomizerPasseordEncoder extends BCryptPasswordEncoder {

    @Autowired
    private OtherService otherService;
    private static final Logger logger = LoggerFactory.getLogger(CustomizerPasseordEncoder.class);

    public CustomizerPasseordEncoder() {
        super(12);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String username = Utils.getRequest().getParameter("username");
        String phoneCode = Utils.getRequest().getParameter(OtherController.PHONECODE_PARAM_NAME);
        if (PhoneUtil.isPhone(username) && StringUtils.hasText(phoneCode)) {
            return otherService.verfyPhoneCode(username,phoneCode);
        }
        logger.info("当前username：{}，当前密码：{}",username,Utils.getRequest().getParameter("password"));
        return super.matches(rawPassword, encodedPassword);
    }
}
