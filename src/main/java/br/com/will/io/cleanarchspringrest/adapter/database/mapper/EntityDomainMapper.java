package br.com.will.io.cleanarchspringrest.adapter.database.mapper;

import br.com.will.io.cleanarchspringrest.adapter.database.entity.EmpresaEntity;
import br.com.will.io.cleanarchspringrest.core.domain.Cnpj;
import br.com.will.io.cleanarchspringrest.core.domain.DataFundacao;
import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import br.com.will.io.cleanarchspringrest.core.domain.Paginado;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class EntityDomainMapper {

  public EmpresaEntity toEntity(Empresa empresa){
    return EmpresaEntity
        .builder()
        .cnpj(empresa.getCnpj().getCnpjComoId())
        .nome(empresa.getNome())
        .nomeFantasia(empresa.getNomeFantasia())
        .dataFundacao(empresa.getDataFundacao().getData())
        .build();
  }

  public Empresa toDomain(EmpresaEntity entity){
    return Empresa
        .builder()
        .cnpj(Cnpj.
            builder()
            .numeros(String.valueOf(entity.getCnpj()))
            .build())
        .nome(entity.getNome())
        .nomeFantasia(entity.getNomeFantasia())
        .dataFundacao(
            DataFundacao
                .builder()
                .data(entity.getDataFundacao())
                .build()
        )
        .build();
  }

  public Paginado<Empresa> toDomain(Page<EmpresaEntity> entities){
    var items = entities.stream().map(this::toDomain).collect(Collectors.toList());

    return Paginado.<Empresa>
        builder()
        .items(items)
        .totalPaginas(entities.getTotalPages())
        .itensTotais(entities.getTotalElements())
        .build();
  }

}
