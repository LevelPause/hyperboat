package com.hyperboat.business.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangweigang
 * @date 2022年09月01日 12:12
 */
@Slf4j
@RestController
public class LoginController {

  @PostMapping("/doLogin")
  public String login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");
    log.info("账号:{},密码:{}", username, password);
    if (StringUtils.equals("root", username) && StringUtils.equals("root", password)) {
      resp.sendRedirect("/");
      return null;
    } else {
      return "账号或密码错误";
    }
  }
}
