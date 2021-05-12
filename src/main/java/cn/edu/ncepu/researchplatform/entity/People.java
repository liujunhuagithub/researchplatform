package cn.edu.ncepu.researchplatform.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class People {

    private Integer id;


    private String username;


    private String password;

    /**
     * 0未认证1已认证2已封号3管理员
     */
    private String auth;

    private String phone;

    private List<Area> areas;

    private Double weight;

    private String nickname;

    private Integer exp;


    private Integer level;


    private String idCard;

    private String realname;

    private String organization;


    private String email;


    private String info;


    private String icon;

    private LocalDateTime gmtCreate;


    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }


}