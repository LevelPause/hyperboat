package com.hyperboat.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author zhangweigang
 * @date 2022年08月31日 2:38
 */
public abstract class MyCollectionUtils {

  private MyCollectionUtils() {
    throw new UnsupportedOperationException("this class can't create newInstance");
  }

  /**
   * 去除集合中pair的value为空的元素
   *
   * @param target 目标集合
   */
  public static List<Pair<String, ?>> wipeNull(List<Pair<String, ?>> target) {
    return target.stream().filter(it -> Objects.nonNull(it.getValue()))
        .collect(Collectors.toList());
  }

  public static <T> void putValue(Map<String, T> map, String key, T value) {
    if (!map.containsKey(key) && value != null) {
      map.put(key, value);
    }
  }
}
