package br.com.will.io.cleanarchspringrest.core.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Cnpj {

  private String numeros;

  public Long getCnpjComoId(){
    return Long.valueOf(this.numeros);
  }
}
