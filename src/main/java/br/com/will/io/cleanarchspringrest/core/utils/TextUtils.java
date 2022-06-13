package br.com.will.io.cleanarchspringrest.core.utils;

public class TextUtils {

  private TextUtils() {
  }

  public static String toSnakeCase(String camelCasedText){

    if(camelCasedText == null || camelCasedText.isEmpty())
      return "";

    var ret = camelCasedText
        .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
        .replaceAll("([a-z])([A-Z])", "$1_$2");
    return ret.toLowerCase();
  }

}
