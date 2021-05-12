package cn.edu.ncepu.researchplatform.common.exception;

public class CustomException extends RuntimeException{
    private int code;
    public CustomException(CustomExceptionType type){
        super(type.message);
        this.code=type.code;
    }





    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
