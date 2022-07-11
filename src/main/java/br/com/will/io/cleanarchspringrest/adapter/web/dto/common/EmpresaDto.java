package br.com.will.io.cleanarchspringrest.adapter.web.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.br.CNPJ;

@SuperBuilder
@Getter
public class EmpresaDto {

  @CNPJ
  private final String cnpj;

  @NotBlank
  @JsonProperty("nome_empresa")
  private final String nomeEmpresa;

  @NotBlank
  @JsonProperty("nome_fantasia")
  private final String nomeFantasia;

  @JsonProperty("data_fundacao")
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "O campo de data deve seguir o padrão yyyy-mm-dd")
  private final String dataFundacao;
}
