package com.hyperboat.business.entity;

import static com.hyperboat.util.MyCollectionUtils.putValue;

import com.hyperboat.jdbc.AbstractEntity;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 * @author zhangweigang
 * @date 2022年08月30日 15:53
 */
@Getter
@Setter
public class User extends AbstractEntity {

  public static final String TABLE_NAME = "user";
  public static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper
      .newInstance(User.class);
  private String username;
  private String password;
  private String displayName;

  private String sex;
  private String mobile;
  private String email;

  public Map<String, Object> fieldValues() {
    Map<String, Object> fieldMap = super.fieldValues();
    putValue(fieldMap, "username", username);
    putValue(fieldMap, "password", password);
    putValue(fieldMap, "display_name", displayName);
    putValue(fieldMap, "sex", sex);
    putValue(fieldMap, "mobile", mobile);
    putValue(fieldMap, "email", email);
    return fieldMap;
  }
}
