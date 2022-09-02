package com.hyperboat.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

public class MyJsonUtils {

  private final static ObjectMapper mapper = ApplicationContextUtils.getBean(ObjectMapper.class);

  private MyJsonUtils() {
    throw new UnsupportedOperationException("this class can't create newInstance");
  }

  public static ObjectMapper getMapper() {
    return mapper;
  }

  /**
   * java对象转jsonString
   *
   * @param value
   *     java对象
   * @return jsonString
   * @throws RuntimeException
   *     写json异常时抛出
   */
  public static String objectToJson(Object value) throws RuntimeException {
    if (value == null) {
      return null;
    }
    try {
      return mapper.writeValueAsString(value);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * jsonString转java对象
   *
   * @param json
   *     jsonString
   * @param valueType
   *     java对象Class类型
   * @param <T>
   *     java对象泛型
   * @return java对象实体
   */
  public static <T> T jsonToObject(String json, Class<T> valueType) {
    if (StringUtils.isBlank(json)) {
      return null;
    }

    try {
      return mapper.readValue(json, valueType);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * jsonString转复杂对象, 如在集合对象: {@code List<PBasicConfig>}
   *
   * @param json
   *     jsonString
   * @param type
   *     <br>复杂对象的{@linkplain TypeReference}</br>
   *     <ul>
   *       <li>{@code new TypeReference<List<PBasicConfig>>(){}}</li>
   *       <li>{@code new TypeReference<Map<String,PBasicConfig>>(){}}</li>
   *     </ul>
   * @param <T>
   *     复杂对象泛型
   * @return 复杂对象
   * @throws RuntimeException
   *     读json异常时抛出
   */
  public static <T> T jsonToObject(String json, TypeReference<T> type){
    try {
      return mapper.readValue(json, type);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * <p><b>不建议使用, 请使用{@linkplain #jsonToObject(String, TypeReference)}</b></p>
   * <p>将jsonArray转换成指定的java对象List</p>
   * <p>注意诸如:{@code List<Date>, List<BigDecimal>, List<BigDecimal>}等简单的java内置对象不支持, 此类请使用{@linkplain
   * #jsonToObject(String, TypeReference)}</p>
   *
   * @param valueType
   *     java对象Class类型
   * @param <T>
   *     java对象泛型
   * @param jsonArray
   *     json字符串
   * @return java对象
   */
 /* public static <T> List<T> jsonToList(String jsonArray, Class<T> valueType) {
    if (StringUtils.isBlank(jsonArray)) {
      return new ArrayList<>();
    }

    List<T> result = new ArrayList<>();
    try {
      JsonArray array = new JsonArray(jsonArray);
      for (int i = 0; i < array.length(); i++) {

        if (valueType == String.class) {
          T jsonObject = (T) array.getString(i);
          result.add(jsonObject);
        } else {
          JsonObject jsonObject = array.getJsonObject(i);
          T object = jsonToObject(jsonObject.toString(), valueType);
          result.add(object);
        }
      }
      return result;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }*/

  /**
   * 通过序列化和反序列化将Object类型转换成指定类型(指定Class)
   *
   * @param source
   *     源对象
   * @param targetType
   *     目标对象Class类型
   * @param <T>
   *     目标对象泛型
   * @return 目标对象
   */
  public static <T> T convert(Object source, Class<T> targetType) {
    String json = objectToJson(source);

    if (StringUtils.isBlank(json)) {
      return null;
    }

    try {
      return mapper.readValue(json, targetType);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
