package br.com.will.io.cleanarchspringrest.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TextUtilsTest {

  @Test
  void deveRetonarSnakeCase(){
    Assertions.assertEquals("camel_case", TextUtils.toSnakeCase("camelCase"));
    Assertions.assertEquals("camel_case", TextUtils.toSnakeCase("CamelCase"));
    Assertions.assertEquals("camel_cased_word", TextUtils.toSnakeCase("CamelCasedWord"));
  }

  @Test
  void naoDeveRetonarErro(){
    Assertions.assertEquals("", TextUtils.toSnakeCase(null));
    Assertions.assertEquals("", TextUtils.toSnakeCase(""));
    Assertions.assertEquals("1", TextUtils.toSnakeCase("1"));
  }
}