package com.hyperboat.config;

import com.hyperboat.util.MyJsonUtils;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class RequestLogAspect {

  @Pointcut("execution(public * com.hyperboat.business.controller..*(..))")
  public void requestServer() {
  }

  @Before("requestServer()")
  public void before(JoinPoint joinPoint) {
    try {
      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
          .getRequestAttributes();
      if (attributes == null) {
        log.info("收到请求，但是看起来请求是空的");
        return;
      }
      if (MDC.get("trace_id") == null) {
        MDC.put("trace_id", UUID.randomUUID().toString());
      }
      HttpServletRequest request = attributes.getRequest();
      StringBuilder sb = new StringBuilder();
      sb.append(
          "\r\n===========================================================================\r\n");
      sb.append("traceid: ").append(MDC.get("trace_id")).append("\r\n");
      sb.append("url: ").append(request.getRequestURL().toString()).append("\r\n");
      sb.append("querys: ").append(request.getQueryString()).append("\r\n");
      // sb.append("body: ").append(body).append("\r\n");
      sb.append("charset: ").append(request.getCharacterEncoding()).append("\r\n");
      sb.append("method: ").append(request.getMethod()).append("\r\n");
      String headers = readRequestHeaders(request);
      sb.append("headers: ").append(headers).append("\r\n");
      String contentType = request.getHeader("content-type");
      if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
        sb.append("params: ").append(MyJsonUtils.objectToJson(joinPoint.getArgs())).append("\r\n");
      }
      sb.append("client: ").append(request.getRemoteAddr()).append("\r\n");
      sb.append("===========================================================================");
      log.info("收到请求:{}", sb.toString());
    } catch (Exception e) {
      log.error("日志切面出错： ", e);
    }
  }

  @Around("requestServer()")
  public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object result = joinPoint.proceed();
    log.info("返回结果，用时{}: \n{}", System.currentTimeMillis() - start,
        MyJsonUtils.objectToJson(result));
    return result;
  }

  @AfterThrowing(pointcut = "requestServer()", throwing = "e")
  public void doAfterThrow(Exception e) {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    log.info("抛出异常", e);
  }

  private static String readRequestHeaders(HttpServletRequest request) {
    Map<String, String> headers = new HashMap<>();

    Enumeration<?> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      String value = request.getHeader(key);
      headers.put(key, value);
    }

    return headers.toString();
  }
}
