package cn.edu.ncepu.researchplatform.common.exception;

public enum CustomExceptionType {
    INPUT_ERROE(400, "输入非法"),
    BLANK_ERROR(404, "无指定内容"),
    AUTH_ERROR(403, "无操作权限"),
    MUST_LOGIN_ERROR(401, "未登录"),
    SYSTEM_ERROR(500,"服务器内部错误");
    public int code;
    public String message;

    private CustomExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
