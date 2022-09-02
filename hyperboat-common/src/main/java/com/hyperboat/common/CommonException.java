package com.hyperboat.common;

import com.hyperboat.util.MyStringUtils;

/**
 * @author zhangweigang
 * @date 2022年08月31日 22:52
 */
public class CommonException extends Exception {

  public static final int FAIL = -1;
  private int code;

  public CommonException() {
    this.code = FAIL;
  }

  public CommonException(String pattern, Object... arguments) {
    super(MyStringUtils.format(pattern, arguments));
    this.code = FAIL;
  }

  public CommonException(int code, String pattern, Object... arguments) {
    super(MyStringUtils.format(pattern, arguments));
    this.code = code;
  }

  public CommonException(Throwable t) {
    super(t);
    this.code = FAIL;
  }

  public CommonException(int code, Throwable t) {
    super(t);
    this.code = code;
  }

  public CommonException(Throwable t, String pattern, Object... arguments) {
    super(MyStringUtils.format(pattern, arguments), t);
    this.code = FAIL;
  }

  public CommonException(int code, Throwable t, String pattern, Object... arguments) {
    super(MyStringUtils.format(pattern, arguments), t);
    this.code = code;
  }

  public int getCode() {
    return this.code;
  }
}
