package br.com.will.io.cleanarchspringrest.adapter.database;

import br.com.will.io.cleanarchspringrest.adapter.database.mapper.EntityDomainMapper;
import br.com.will.io.cleanarchspringrest.adapter.database.repository.EmpresaRepository;
import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import br.com.will.io.cleanarchspringrest.core.ports.EmpresaRepositoryPort;
import br.com.will.io.cleanarchspringrest.mock.EmpresaMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EmpresaRepositoryPortImplTest {

  @Autowired
  private EmpresaRepository empresaRepository;

  private EmpresaRepositoryPort repositoryPort;

  private final Empresa empresaX = EmpresaMock.criarEmpresaDomainX();
  private final Empresa empresaY = EmpresaMock.criarEmpresaDomainY();

  @BeforeEach
  void setUp() {
    repositoryPort = new EmpresaRepositoryPortImpl(empresaRepository, new EntityDomainMapper());
    criarEmpresaX();
    criarEmpresaY();
  }

  @Test
  void deveCriarUmaEmpresaComSucesso() {

    var empresaBD = repositoryPort.buscarPorDocumento(empresaX.getCnpj().getNumeros());

    Assertions.assertTrue(empresaBD.isPresent());
    validarIgualdade(empresaX, empresaBD.get());
  }

  @Test
  void deveAlterarUmaEmpresaComSucesso() {
    var empresaAlterada = Empresa
        .builder()
        .identificador(empresaX.getIdentificador())
        .cnpj(empresaX.getCnpj())
        .nomeFantasia(empresaX.getNomeFantasia())
        .nome(empresaX.getNome())
        .dataFundacao(empresaX.getDataFundacao())
        .build();

    repositoryPort.atualizarEmpresa(empresaAlterada);

    var empresaBD = repositoryPort.buscarPorDocumento(empresaX.getCnpj().getNumeros());

    Assertions.assertTrue(empresaBD.isPresent());
    validarIgualdade(empresaX, empresaBD.get());
  }

  @Test
  void deveRetornarEmpresaPorDocumento() {

    var empresaBD = repositoryPort.buscarPorDocumento(empresaX.getCnpj().getNumeros());

    Assertions.assertTrue(empresaBD.isPresent());
    validarIgualdade(empresaX, empresaBD.get());
  }

  @Test
  void buscarTodos() {
    var retornarTodosDoisItems = repositoryPort.buscarTodos(1, 10);

    Assertions.assertEquals(2, retornarTodosDoisItems.getItems().size());
    Assertions.assertEquals(2, retornarTodosDoisItems.getItensTotais());
    Assertions.assertEquals(10, retornarTodosDoisItems.getItensPorPagina());
    Assertions.assertEquals(1, retornarTodosDoisItems.getPagina());

    var empresaXBD = retornarTodosDoisItems.getItems().get(0);
    var empresaYBD = retornarTodosDoisItems.getItems().get(1);

    validarIgualdade(empresaX, empresaXBD);
    validarIgualdade(empresaY, empresaYBD);
  }

  @Test
  void excluir() {
    repositoryPort.excluir(empresaX.getCnpj().getNumeros());

    deveRetornarVazio();
  }

  private void deveRetornarVazio() {
    Assertions.assertFalse(
        repositoryPort.buscarPorDocumento(empresaX.getCnpj().getNumeros()).isEmpty()
    );
  }

  private void criarEmpresaX() {
    repositoryPort.novaEmpresa(empresaX);
  }

  private void criarEmpresaY() {
    repositoryPort.novaEmpresa(empresaY);
  }

  private void validarIgualdade(Empresa empresa1, Empresa empresa2){
    Assertions.assertEquals(empresa1.getCnpj().getNumeros(), empresa2.getCnpj().getNumeros());
    Assertions.assertEquals(empresa1.getNome(), empresa2.getNome());
    Assertions.assertEquals(empresa1.getNomeFantasia(), empresa2.getNomeFantasia());
    Assertions.assertEquals(empresa1.getDataFundacao().getDataFormatadaString(), empresa2.getDataFundacao().getDataFormatadaString());
  }

}