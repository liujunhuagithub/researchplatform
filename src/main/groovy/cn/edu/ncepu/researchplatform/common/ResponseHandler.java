package cn.edu.ncepu.researchplatform.common;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.common.exception.CustomExceptionType;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof R) {
            response.setStatusCode(HttpStatus.OK);
            return body;
        }
        return R.success(body);
    }


    @ExceptionHandler(CustomException.class)
    public R customError(CustomException exception) {
        return R.fail(exception);
    }

    @ExceptionHandler(BindException.class)
    public R validateError(BindException exception) {
        return R.fail(400, exception.getFieldError().getDefaultMessage());
    }
    @ExceptionHandler({IllegalArgumentException.class, ConversionFailedException.class})
    public R inputError(Exception exception) {
        return R.fail(CustomExceptionType.INPUT_ERROE);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R accessDeniedHandler(AccessDeniedException exception) {
        return R.fail(CustomExceptionType.AUTH_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public R error(Exception exception) {
        return R.fail(CustomExceptionType.SYSTEM_ERROR);
    }

}
