package com.hyperboat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//todo 配置
@EnableConfigurationProperties
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    // 设置日期格式
    SimpleModule module = new SimpleModule();
    module.addSerializer(Date.class,
        new DateSerializer(false, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
    module.addDeserializer(Date.class, new DateDeserializer() {
      @Override
      public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (_customFormat != null) {
          return _parseDate(jp, ctxt);
        }
        ObjectCodec codec = jp.getCodec();
        JsonNode tree = codec.readTree(jp);
        if (tree instanceof TextNode) {
          String dateString = tree.textValue();
          if (StringUtils.isBlank(dateString)) {
            return null;
          }
          try {
            return DateUtils
                .parseDate(dateString.replaceAll("Z$", "+0000"), "yyyy-MM-dd'T'HH:mm:ss",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-dd", "yyyy/MM/dd");
          } catch (ParseException e) {
            throw new IOException(e);
          }
        } else if (tree instanceof LongNode) {
          return new Date(tree.longValue());
        } else {
          throw new IllegalArgumentException("Unknow Tree Node.");
        }

      }
    });
    objectMapper.registerModule(module);
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return objectMapper;
  }

}
