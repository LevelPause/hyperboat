package com.hyperboat.util;

import java.text.MessageFormat;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author zhangweigang
 * @date 2022年08月31日 22:55
 */
public class MyStringUtils {

  public static String format(String pattern, Object... arguments) {
    if (pattern.contains("{}")) {
      FormattingTuple ft = MessageFormatter.arrayFormat(pattern, arguments);
      return ft.getMessage();
    } else {
      return MessageFormat.format(pattern, arguments);
    }
  }
}
