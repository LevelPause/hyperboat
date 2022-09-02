package com.hyperboat.business.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhangweigang
 * @date 2022年09月01日 11:28
 */
@Controller
public class PageController {

  @GetMapping({"/", "index"})
  public String index() {
    return "index";
  }
  @GetMapping({"login"})
  public String login() {
    return "login";
  }
}
