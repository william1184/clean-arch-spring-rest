package br.com.will.io.cleanarchspringrest.adapter.database.entity;

import br.com.will.io.cleanarchspringrest.core.domain.Cnpj;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class EmpresaEntity {

  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true)
  private Long cnpj;

  @Setter
  private String nome;

  @Setter
  private String nomeFantasia;

  @Setter
  private LocalDate dataFundacao;

  @CreatedDate
  private LocalDateTime dataCriacao;

  @LastModifiedDate
  private LocalDateTime dataUltimaAtualizacao;

  public String getCnpjComoString() {
    return Cnpj.transformarCnpjLongParaString(this.cnpj);
  }
}
