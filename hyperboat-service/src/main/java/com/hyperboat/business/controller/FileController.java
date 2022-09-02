package com.hyperboat.business.controller;

import com.hyperboat.common.CommonResponse;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhangweigang
 * @date 2022年09月01日 0:47
 */
@Slf4j
@RestController
@RequestMapping(value = "v1/file")
public class FileController {

  @Value("${file.upload.url}")
  private String uploadFilePath;

  @RequestMapping("/upload")
  public CommonResponse<Boolean> upload(@RequestParam("files") MultipartFile[] files) {
    for (MultipartFile file : files) {
      String fileName = file.getOriginalFilename();  // 文件名
      File dest = new File(uploadFilePath + '/' + fileName);
      if (!dest.getParentFile().exists()) {
        dest.getParentFile().mkdirs();
      }
      try {
        file.transferTo(dest);
        log.info("上传成功! 路径为:{}", dest.getAbsolutePath());
      } catch (Exception e) {
        log.error("程序错误，请重新上传", e);
        return CommonResponse.fail(false, "程序错误，请重新上传");
      }
    }
    return CommonResponse.success(true);
  }

  @RequestMapping("/download")
  public CommonResponse<Boolean> download() {
    return CommonResponse.success(true);
  }
}
