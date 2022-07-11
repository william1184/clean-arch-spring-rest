package br.com.will.io.cleanarchspringrest.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class ConverterUtils {
  private static final ObjectMapper mapper = new ObjectMapper();

  private ConverterUtils(){
  }

  public static <T> T converterJsonEmObjeto(Class<T> clazz, String json){
    try {
      return mapper.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      return null;
    }
  }
}
