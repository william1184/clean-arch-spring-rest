package br.com.will.io.cleanarchspringrest.core.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import br.com.will.io.cleanarchspringrest.core.exceptions.EmpresaNaoEncontradaException;
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
    BuscarEmpresaPorDocumentoUseCase.class
})
class BuscarEmpresaPorDocumentoUseCaseTest {

  @Autowired
  private BuscarEmpresaPorDocumentoUseCase useCase;

  @MockBean
  private EmpresaRepositoryPort mockRepository;

  @Captor
  private ArgumentCaptor<Empresa> empresaArgumentCaptor;

  @Test
  void deveRetornarEmpresaBuscada() {
    var retornoBD = EmpresaMock.criarEmpresaDomainX();

    when(mockRepository.buscarPorDocumento(anyLong())).thenReturn(Optional.of(retornoBD));

    useCase.execute(
        retornoBD.getCnpj().getDocumento()
    ).orElseThrow();

    deveInvocarBuscaPorDoc();
  }

  @Test
  void deveRetornarErroQuandoItemNaoExisteAEmpresa() {
    when(mockRepository.buscarPorDocumento(anyLong())).thenReturn(Optional.empty());

    var domain = EmpresaMock.criarEmpresaDomainX();
    var documento = domain.getCnpj().getDocumento();

    Assertions.assertThrows(EmpresaNaoEncontradaException.class, () ->
        useCase.execute(documento)
    );

    deveInvocarBuscaPorDoc();

  }

  private void deveInvocarBuscaPorDoc() {
    verify(mockRepository, times(1)).buscarPorDocumento(anyLong());
  }
}