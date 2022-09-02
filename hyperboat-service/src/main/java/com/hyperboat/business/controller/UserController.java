package com.hyperboat.business.controller;

import com.hyperboat.business.entity.User;
import com.hyperboat.business.service.UserService;
import com.hyperboat.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangweigang
 * @date 2022年08月31日 18:39
 */
@Slf4j
@RestController
@RequestMapping(value = "v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/{userUuid}")
  public CommonResponse<User> query(@PathVariable(value = "userUuid") String userUuid) {
    User user = userService.findByUuid(userUuid);
    return CommonResponse.success(user);
  }

  @PostMapping("/delete/{userUuid}")
  public CommonResponse<Boolean> delete(@PathVariable(value = "userUuid") String userUuid) {
    boolean success = userService.delete(userUuid);
    return CommonResponse.success(success);
  }

  @PostMapping("/insert")
  public CommonResponse<User> insert(@RequestBody User request) {
    User response = userService.insertUser(request);
    return CommonResponse.success(response);
  }

  @PostMapping("/update")
  public CommonResponse<User> update(@RequestBody User request) {
    User reponse = userService.update(request);
    return CommonResponse.success(reponse);
  }
}
