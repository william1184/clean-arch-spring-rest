package br.com.will.io.cleanarchspringrest.adapter.database.mapper;

import static org.junit.jupiter.api.Assertions.*;

import br.com.will.io.cleanarchspringrest.mock.EmpresaMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
    EntityDomainMapper.class
})
class EntityDomainMapperTest {

  @Autowired
  private EntityDomainMapper mapper;

  @Test
  void deveConverterDomainParaEntidade(){
    var dominio = EmpresaMock.criarEmpresaDomainX();

    var entidade = mapper.toEntity(dominio);

    assertEquals(dominio.getCnpj().getNumeros(), entidade.getCnpj() );
    assertEquals(dominio.getNome(), entidade.getNome() );
    assertEquals(dominio.getNomeFantasia(), entidade.getNomeFantasia() );
    assertEquals(dominio.getDataFundacao().getData(), entidade.getDataFundacao() );
  }

  @Test
  void deveConverterEntidadeParaDomain(){
    var entidade = EmpresaMock.criarEmpresaEntidadeDbX();

    var dominio = mapper.toDomain(entidade);

    assertEquals(entidade.getCnpj(), dominio.getCnpj().getNumeros() );
    assertEquals(entidade.getNome(), dominio.getNome() );
    assertEquals(entidade.getNomeFantasia(), dominio.getNomeFantasia() );
    assertEquals(entidade.getDataFundacao(), dominio.getDataFundacao().getData());
  }

}