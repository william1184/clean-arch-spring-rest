package br.com.will.io.cleanarchspringrest.adapter.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.will.io.cleanarchspringrest.adapter.web.dto.common.EmpresaDto;
import br.com.will.io.cleanarchspringrest.adapter.web.dto.common.ResponseDto;
import br.com.will.io.cleanarchspringrest.mock.EmpresaMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
    RequestResponseMapper.class
})
class RequestResponseMapperTest {

  @Autowired
  private RequestResponseMapper mapper;

  @Test
  void deveConverterRequestParaDomain(){
    var request = EmpresaMock.criarEmpresaRequestX();

    var dominio = mapper.toDomain(request);

    assertEquals(request.getCnpj(), dominio.getCnpj().getDocumento() );
    assertEquals(request.getNomeEmpresa(), dominio.getNome() );
    assertEquals(request.getNomeFantasia(), dominio.getNomeFantasia() );
    assertEquals(request.getDataFundacao(), dominio.getDataFundacao().getDataFormatadaString() );
  }

  @Test
  void deveConverterDomainParaResponse(){
    var dominio = EmpresaMock.criarEmpresaDomainX();

    var response = (EmpresaDto) mapper.toResponse(dominio).getData();

    assertEquals(dominio.getCnpj().getDocumento(), response.getCnpj() );
    assertEquals(dominio.getNome(), response.getNomeEmpresa() );
    assertEquals(dominio.getNomeFantasia(), response.getNomeFantasia() );
    assertEquals(dominio.getDataFundacao().getDataFormatadaString(), response.getDataFundacao());
  }

}