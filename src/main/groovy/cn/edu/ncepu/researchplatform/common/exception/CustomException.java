package cn.edu.ncepu.researchplatform.common.exception;

public class CustomException extends RuntimeException {
    private int code;
    public static CustomException INPUT_ERROE_Exception = new CustomException(CustomExceptionType.INPUT_ERROE);
    public static CustomException SENSITIVE_ERROR_Exception = new CustomException(CustomExceptionType.SENSITIVE_ERROR);
    public static CustomException AUTH_ERROR_Exception = new CustomException(CustomExceptionType.AUTH_ERROR);
    public static CustomException MUST_LOGIN_ERROR_Exception = new CustomException(CustomExceptionType.MUST_LOGIN_ERROR);
    public static CustomException SYSTEM_ERROR_Exception = new CustomException(CustomExceptionType.SYSTEM_ERROR);
    public static CustomException AREA_ERROR_Exception = new CustomException(CustomExceptionType.AREA_ERROR);

    private CustomException(CustomExceptionType type) {
        super(type.message);
        this.code = type.code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
