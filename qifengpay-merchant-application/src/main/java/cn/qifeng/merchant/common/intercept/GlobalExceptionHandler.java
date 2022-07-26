package cn.qifeng.merchant.common.intercept;

import cn.qifeng.common.domain.BusinessException;
import cn.qifeng.common.domain.CommonErrorCode;
import cn.qifeng.common.domain.ErrorCode;
import cn.qifeng.common.domain.RestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static  final Logger LOGGER= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //捕获Exception异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse processException( HttpServletRequest request,
                                               HttpServletResponse response,
                                               Exception e){
        //解析异常信息
        // 自定义直接取出errcode and errmessage
        if(e instanceof BusinessException){
            LOGGER.info(e.getMessage(),e);
            BusinessException businessException = (BusinessException) e;
            ErrorCode errorCode = businessException.getErrorCode();
//            错误代码
            int code = errorCode.getCode();
//            错误信息
            String desc = errorCode.getDesc();
            return new RestErrorResponse(String.valueOf(code),desc);
        }

        //系统异常
        LOGGER.error("系统异常,请联系管理员：",e);
        return new RestErrorResponse(String.valueOf(CommonErrorCode.UNKNOWN.getCode()),CommonErrorCode.UNKNOWN.getDesc());
    }
}
