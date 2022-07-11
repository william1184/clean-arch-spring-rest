package br.com.will.io.cleanarchspringrest.core.domain;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Builder
@Getter
public class Cnpj {
  public static final int QUANTIDADE_NUMEROS_CNPJ = 14;

  private Long numeros;

  public String getDocumento(){
    return transformarCnpjLongParaString(this.numeros);
  }

  public static String transformarCnpjLongParaString(Long numeros) {
    return StringUtils.leftPad(String.valueOf(numeros), QUANTIDADE_NUMEROS_CNPJ, '0');
  }
}
