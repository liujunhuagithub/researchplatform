package cn.edu.ncepu.researchplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class OtherService {
    @Autowired
    @Qualifier("captcha")
    private Cache captchaCache;
    public String phoneCode(String phone){
        //暂定phone前6位
        return phone.substring(0,6);
    }

    public boolean verfyPhoneCode(String phone,String phoneCode){
        return captchaCache.get(phone,String.class).equals(phoneCode);
    }
}
