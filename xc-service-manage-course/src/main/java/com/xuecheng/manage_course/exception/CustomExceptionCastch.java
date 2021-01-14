package com.xuecheng.manage_course.exception;


import com.xuecheng.framework.exception.ExceptionCatch;
import com.xuecheng.framework.model.response.CommonCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author 周文韬
 * @create 2021-01-12 13:05:54
 * @desc ...
 */
@ControllerAdvice
public class CustomExceptionCastch extends ExceptionCatch {
    /**
     * 定义自己的错误类型响应信息
     */
    static {
        builder.put(AccessDeniedException.class, CommonCode.UNAUTHORISE);

    }

}
