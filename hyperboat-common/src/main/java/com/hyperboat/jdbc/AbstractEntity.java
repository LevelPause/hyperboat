package com.hyperboat.jdbc;

import static com.hyperboat.util.MyCollectionUtils.putValue;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangweigang
 * @date 2022年08月31日 2:43
 */
@Getter
@Setter
public abstract class AbstractEntity {

  /**
   * 逻辑主键
   */
  private String uuid;
  /**
   * 修改时间
   */
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
  private Date modifyTime;
  /**
   * 修改人
   */
  private String modifier;
  /**
   * 创建时间
   */
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
  private Date createTime;
  /**
   * 创建人
   */
  private String creator;

  public Map<String, Object> fieldValues() {
    HashMap<String, Object> fieldMap = new HashMap<>();
    putValue(fieldMap, "uuid", uuid);
    putValue(fieldMap, "modify_time", modifyTime);
    putValue(fieldMap, "modifier", modifier);
    putValue(fieldMap, "create_time", createTime);
    putValue(fieldMap, "creator", creator);
    return fieldMap;
  }
}
