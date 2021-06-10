package cn.edu.ncepu.researchplatform.service;

import org.springframework.stereotype.Service;

@Service
public class OtherService {
    public String phoneCode(String phone){
        //暂定phone前6位
        return phone.substring(0,6);
    }
}
