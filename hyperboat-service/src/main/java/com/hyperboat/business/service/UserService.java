package com.hyperboat.business.service;

import com.hyperboat.business.dao.UserDao;
import com.hyperboat.business.entity.User;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangweigang
 * @date 2022年08月31日 18:32
 */
@Service
public class UserService {

  @Autowired
  private UserDao userDao;

  public User findByUuid(String uuid) {
    return userDao.queryById(uuid);
  }

  public User insertUser(User user) {
    if (user.getUuid() == null) {
      user.setUuid(UUID.randomUUID().toString());
    }
    int insert = userDao.insert(user);
    if (insert == 1) {
      return userDao.queryById(user.getUuid());
    } else {
      throw new RuntimeException("新增失败!");
    }
  }

  public User update(User user) {
    if (user.getUuid() == null) {
      throw new RuntimeException("用户不存在!");
    }
    int update = userDao.updateByUuid(user);
    if (update == 1) {
      return userDao.queryById(user.getUuid());
    } else {
      throw new RuntimeException("用户不存在!");
    }
  }

  public boolean delete(String userUuid) {
    return userDao.deleteById(userUuid) == 1;
  }
}
