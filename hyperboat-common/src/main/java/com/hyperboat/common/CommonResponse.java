package com.hyperboat.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangweigang
 * @date 2022年08月31日 18:42
 */
@Getter
@Setter
public class CommonResponse<T> {

  public static final int SUCCESS = 0;
  public static final int FAIL = -1;

  private int code;
  private String msg;
  private T data;

  public boolean isSuccess() {
    return code == SUCCESS;
  }

  public static <T> CommonResponse<T> success(T data) {
    CommonResponse<T> response = new CommonResponse<>();
    response.setCode(SUCCESS);
    response.setData(data);
    return response;
  }

  public static <T> CommonResponse<T> fail(T data) {
    CommonResponse<T> response = new CommonResponse<>();
    response.setCode(FAIL);
    response.setData(data);
    return response;
  }

  public static CommonResponse<Void> success() {
    CommonResponse<Void> response = new CommonResponse<>();
    response.setCode(SUCCESS);
    response.setData(null);
    return response;
  }

  public static CommonResponse<Void> fail() {
    CommonResponse<Void> response = new CommonResponse<>();
    response.setCode(FAIL);
    response.setData(null);
    return response;
  }

  public static <T> CommonResponse<T> fail(T data, String msg) {
    CommonResponse<T> response = new CommonResponse<>();
    response.setCode(FAIL);
    response.setData(data);
    response.setMsg(msg);
    return response;
  }
}
