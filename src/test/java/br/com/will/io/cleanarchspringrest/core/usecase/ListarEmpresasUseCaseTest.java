package br.com.will.io.cleanarchspringrest.core.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import br.com.will.io.cleanarchspringrest.core.domain.Paginado;
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
    ListarEmpresasUseCase.class
})
class ListarEmpresasUseCaseTest {

  @Autowired
  private ListarEmpresasUseCase useCase;

  @MockBean
  private EmpresaRepositoryPort mockRepository;

  @Captor
  private ArgumentCaptor<Empresa> empresaArgumentCaptor;

  @Test
  void deveRetornarUmaPagina() {
    var retornoBD = EmpresaMock.criarEmpresaDomainX();

    when(mockRepository.buscarTodos(anyInt(), anyInt())).thenReturn(EmpresaMock.criarEmpresaPaginada());

    var retorno = useCase.execute("0", "10");

    deveInvocarPaginado();
  }

  private void deveInvocarPaginado() {
    verify(mockRepository, times(1)).buscarTodos(anyInt(), anyInt());
  }

}