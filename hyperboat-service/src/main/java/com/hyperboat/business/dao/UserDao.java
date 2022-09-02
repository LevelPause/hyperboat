package com.hyperboat.business.dao;

import com.hyperboat.business.entity.User;
import com.hyperboat.jdbc.AbstractDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author zhangweigang
 * @date 2022年08月31日 0:47
 */
@Repository
public class UserDao extends AbstractDao<User> {

  public UserDao(JdbcTemplate jdbcTemplate,
      NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    super(jdbcTemplate, namedParameterJdbcTemplate);
  }

  @Override
  protected RowMapper<User> getRowMapper() {
    return User.ROW_MAPPER;
  }

  @Override
  protected String getTableName() {
    return User.TABLE_NAME;
  }
}
