package com.hyperboat.config;

/**
 * @author zhangweigang
 * @date 2022年08月31日 22:50
 */

import static org.springframework.http.HttpStatus.OK;

import com.hyperboat.common.CommonException;
import com.hyperboat.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常拦截
 */
@Slf4j
@ControllerAdvice(value = {"com.hyperboat.business.controller"})
public class RestExceptionHandler {

  @ResponseBody
  @ExceptionHandler(Throwable.class)
  @ResponseStatus(OK)
  public CommonResponse<?> restExceptionHandler(Exception ex) {
    return buildResponse(ex);
  }

  public static CommonResponse<?> buildResponse(Throwable e) {
    log.error("请求响应异常:", e);
    CommonResponse<?> response = new CommonResponse<>();
    if (e instanceof CommonException) {
      response.setCode(((CommonException) e).getCode());
      response.setMsg(e.getMessage());
    } else {
      response.setCode(-1);
      response.setMsg("业务异常，请联系开发人员。");
    }
    return response;
  }
}
