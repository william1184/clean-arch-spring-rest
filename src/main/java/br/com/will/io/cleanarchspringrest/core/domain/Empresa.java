package br.com.will.io.cleanarchspringrest.core.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Empresa {

    private Long identificador;
    private final Cnpj cnpj;
    private final String nome;
    private final String nomeFantasia;
    private final DataFundacao dataFundacao;
    private final List<Endereco> enderecos;

    public void adicionarIdentificador(Long identificador) {
        this.identificador = identificador;
    }
}
