package cn.edu.ncepu.researchplatform.service;

import cn.edu.ncepu.researchplatform.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class OtherService {
    @Autowired
    @Qualifier("code")
    private Cache codeCache;
    public static String sp = "`";

    public String phoneCode(String phone) {
        //暂定phone前6位
        return phone.substring(0, 6);
    }

    public boolean verfyPhoneCode(String phone, String phoneCode) {
        return codeCache.get(phone, String.class).equals(phoneCode);
    }

    public static boolean isReadName(String idCard, String realname) {
        return Math.random() > 0.05;
    }

    public static boolean isIllegalEvaluate(String content) {
        //审核Evaluate，暂时95%通过率
        return Math.random() < 0.05;
    }

    public static boolean isIllegalFile(File file) {
//        return Math.random() < 0.05;
        return false;
    }

}
