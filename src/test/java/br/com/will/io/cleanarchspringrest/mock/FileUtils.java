package br.com.will.io.cleanarchspringrest.mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class FileUtils {

  private FileUtils(){
  }

  public static String getJson(String nomeArquivo){
    try{
      var path = Paths.get(MessageFormat.format("src/test/resources/json/{0}.json", nomeArquivo));

      return String.join("", Files.readAllLines(path));
    } catch (IOException e) {
      throw new RuntimeException("Erro");
    }
  }
}
