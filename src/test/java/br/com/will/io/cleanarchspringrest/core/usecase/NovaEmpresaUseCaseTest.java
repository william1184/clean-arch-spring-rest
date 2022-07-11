package br.com.will.io.cleanarchspringrest.core.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import br.com.will.io.cleanarchspringrest.core.exceptions.EmpresaJaExisteException;
import br.com.will.io.cleanarchspringrest.core.ports.EmpresaRepositoryPort;
import br.com.will.io.cleanarchspringrest.mock.EmpresaMock;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {
    NovaEmpresaUseCase.class
})
class NovaEmpresaUseCaseTest {

  @Autowired
  private NovaEmpresaUseCase useCase;

  @MockBean
  private EmpresaRepositoryPort mockRepository;

  @Captor
  private ArgumentCaptor<Empresa> empresaArgumentCaptor;

  @Test
  void deveCriarNovaEmpresaComSucesso(){
    var retornoBD = EmpresaMock.criarEmpresaDomainX();

    when(mockRepository.buscarPorDocumento(anyLong())).thenReturn(Optional.empty());
    when(mockRepository.novaEmpresa(empresaArgumentCaptor.capture())).thenReturn(Optional.of(retornoBD));

    var novaEmpresa = useCase.execute(EmpresaMock.criarEmpresaDomainX()).orElseThrow();

    Assertions.assertEquals(retornoBD.getCnpj().getDocumento(), novaEmpresa.getCnpj().getDocumento());
    Assertions.assertEquals(retornoBD.getNome(), novaEmpresa.getNome());
    Assertions.assertEquals(retornoBD.getNomeFantasia(), novaEmpresa.getNomeFantasia());
    Assertions.assertEquals(retornoBD.getDataFundacao(), novaEmpresa.getDataFundacao());

    deveInvocarBuscaPorDoc();
    deveInvocarSalvarAtualizar();
  }

  @Test
  void deveRetornarErroQuandoItemJaExisteNoBanco(){
    var retornoBD = EmpresaMock.criarEmpresaDomainX();

    when(mockRepository.buscarPorDocumento(anyLong())).thenReturn(Optional.of(retornoBD));

    var domain = EmpresaMock.criarEmpresaDomainX();

    Assertions.assertThrows(EmpresaJaExisteException.class, () ->
        useCase.execute(domain)
    );

    deveInvocarBuscaPorDoc();
    naoDeveInvocarSalvarAtualizar();

  }

  private void deveInvocarBuscaPorDoc(){
    verify(mockRepository, times(1)).buscarPorDocumento(anyLong());
  }

  private void deveInvocarSalvarAtualizar(){
    verify(mockRepository, times(1))
        .novaEmpresa(any(Empresa.class));
  }

  private void naoDeveInvocarSalvarAtualizar(){
    verify(mockRepository, never())
        .novaEmpresa(any(Empresa.class));
  }

}