package br.com.will.io.cleanarchspringrest.mock;

import br.com.will.io.cleanarchspringrest.adapter.database.entity.EmpresaEntity;
import br.com.will.io.cleanarchspringrest.adapter.database.mapper.EntityDomainMapper;
import br.com.will.io.cleanarchspringrest.adapter.web.dto.EmpresaRequest;
import br.com.will.io.cleanarchspringrest.adapter.web.mapper.RequestResponseMapper;
import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import br.com.will.io.cleanarchspringrest.core.domain.Paginado;
import java.util.List;

public class EmpresaMock {

  public static final String NOME_NOVO = "Nome novo";
  public static final String NOME_FANTASIA_NOVO = "Nome fantasia novo";

  public static EmpresaRequest criarEmpresaRequestX(){
    return ConverterUtils.converterJsonEmObjeto(EmpresaRequest.class, criarEmpresaRequestStringX());
  }

  public static EmpresaRequest criarEmpresaRequestY(){
    return ConverterUtils.converterJsonEmObjeto(EmpresaRequest.class, criarEmpresaRequestStringY());
  }

  public static Empresa criarEmpresaDomainX(){
    return new RequestResponseMapper().toDomain(criarEmpresaRequestX());
  }

  public static Empresa criarEmpresaDomainY(){
    return new RequestResponseMapper().toDomain(criarEmpresaRequestY());
  }

  public static EmpresaEntity criarEmpresaEntidadeDbX(){
    return new EntityDomainMapper().toEntity(criarEmpresaDomainX());
  }

  public static EmpresaEntity criarEmpresaEntidadeDbY(){
    return new EntityDomainMapper().toEntity(criarEmpresaDomainX());
  }

  public static Paginado<Empresa> criarEmpresaPaginada(){
    return Paginado.<Empresa>builder()
        .items(List.of(criarEmpresaDomainX(), criarEmpresaDomainY()))
        .itensPorPagina(10)
        .itensTotais(2)
        .pagina(0)
        .totalPaginas(1).build();
  }

  public static String criarEmpresaRequestStringX(){
    return FileUtils.getJson("empresa_request_x");
  }

  public static String criarEmpresaRequestStringY(){
    return FileUtils.getJson("empresa_request_y");
  }

}
