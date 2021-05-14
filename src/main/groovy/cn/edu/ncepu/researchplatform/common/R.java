package cn.edu.ncepu.researchplatform.common;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.common.exception.CustomExceptionType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.nio.charset.StandardCharsets;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class R {
    private int code;
    private String message;
    private Object data;

    public static R fail(int code, String meaasge) {
        return new R(code, meaasge, null);
    }

    public static R success(Object data) {
        return new R(200, null, data);
    }

    public static R fail(CustomException exception) {
        return new R(exception.getCode(), exception.getMessage(), null);
    }

    private R(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
