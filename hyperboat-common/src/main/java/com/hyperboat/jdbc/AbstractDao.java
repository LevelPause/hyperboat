package com.hyperboat.jdbc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author zhangweigang
 * @date 2022年08月31日 2:07
 */
public abstract class AbstractDao<Entity extends AbstractEntity> {

  protected final JdbcTemplate jdbcTemplate;
  protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public AbstractDao(JdbcTemplate jdbcTemplate,
      NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  protected abstract RowMapper<Entity> getRowMapper();

  protected abstract String getTableName();


  public int updateByUuid(Entity entity) {
    return namedParameterJdbcTemplate.update(updateSql(entity), entity.fieldValues());
  }

  public int insert(Entity entity) {
    return namedParameterJdbcTemplate.update(insertSql(entity), entity.fieldValues());
  }

  @Nullable
  public Entity queryById(String uuid) {
    String sql = "select * from " + getTableName() + " where uuid = ?";
    return jdbcTemplate.queryForObject(sql, getRowMapper(), uuid);
  }

  @Nullable
  public List<Entity> queryList(QueryCondition qc) {
    String sql = "select * from " + getTableName() + qc.sql();
    List<Entity> resultList = namedParameterJdbcTemplate.query(sql, qc.param(), getRowMapper());
    if (CollectionUtils.isNotEmpty(resultList)) {
      return resultList;
    }
    return new ArrayList<>();
  }

  public int deleteById(String uuid) {
    String sql = "delete from " + getTableName() + " where uuid = ?";
    return jdbcTemplate.update(sql, uuid);
  }

  private String updateSql(Entity entity) {
    Map<String, Object> saveValues = entity.fieldValues();
    List<String> keyValueList = new ArrayList<>();
    for (String key : saveValues.keySet()) {
      if (StringUtils.equalsIgnoreCase("uuid", key)) {
        continue;
      }
      keyValueList.add(key + "=:" + key);
    }
    String keyValues = String.join(", ", keyValueList);
    return "update " + getTableName() + " set " + keyValues + " where uuid =:uuid";
  }

  private String insertSql(Entity entity) {
    Map<String, Object> saveValues = entity.fieldValues();
    String[] keyList = saveValues.keySet().toArray(new String[]{});
    String keys = String.join(",", keyList);
    for (int i = 0; i < keyList.length; i++) {
      keyList[i] = ":" + keyList[i];
    }
    String placeholders = String.join(",", keyList);
    return "insert into " + getTableName() + " (" + keys + ") values (" + placeholders + ")";
  }
}
