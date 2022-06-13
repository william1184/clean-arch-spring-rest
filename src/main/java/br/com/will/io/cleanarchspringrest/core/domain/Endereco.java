package br.com.will.io.cleanarchspringrest.core.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Endereco {

    private final String logradouro;
    private final String numero;
    private final String cep;
    private final String bairro;
    private final String unidadeFederativa;
    private final String pais;

}
