package br.com.will.io.cleanarchspringrest.core.usecase;

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
    AtualizarEmpresaUseCase.class
})
class AtualizarEmpresaUseCaseTest {

  @Autowired
  private AtualizarEmpresaUseCase useCase;

  @MockBean
  private EmpresaRepositoryPort mockRepository;

  @Captor
  private ArgumentCaptor<Empresa> empresaArgumentCaptor;

  @Test
  void deveAtualizarComSucesso(){
    var retornoBD = EmpresaMock.criarEmpresaDomainX();

    when(mockRepository.buscarPorDocumento(anyLong())).thenReturn(Optional.of(retornoBD));
    when(mockRepository.novaEmpresa(empresaArgumentCaptor.capture())).thenReturn(Optional.of(retornoBD));

    var solicitacaoALteracao = Empresa
        .builder()
        .nome(EmpresaMock.NOME_NOVO)
        .nomeFantasia(EmpresaMock.NOME_FANTASIA_NOVO)
        .build();

    useCase.execute(
        retornoBD.getCnpj().getDocumento(), solicitacaoALteracao
    ).orElseThrow();

    var alteradoCaptor = empresaArgumentCaptor.getValue();

    Assertions.assertEquals(retornoBD.getCnpj().getDocumento(), alteradoCaptor.getCnpj().getDocumento());
    Assertions.assertEquals(EmpresaMock.NOME_NOVO, alteradoCaptor.getNome());
    Assertions.assertEquals(EmpresaMock.NOME_FANTASIA_NOVO, alteradoCaptor.getNomeFantasia());
    Assertions.assertEquals(retornoBD.getDataFundacao(), alteradoCaptor.getDataFundacao());

    deveInvocarBuscaPorDoc();
    deveInvocarSalvarAtualizar();
  }

  @Test
  void deveRetornarErroQuandoItemNaoExisteNoBanco(){
    when(mockRepository.novaEmpresa(any())).thenReturn(Optional.empty());

    var domain = EmpresaMock.criarEmpresaDomainX();
    var documento = domain.getCnpj().getDocumento();

    Assertions.assertThrows(EmpresaNaoEncontradaException.class, () ->
      useCase.execute(documento, domain)
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