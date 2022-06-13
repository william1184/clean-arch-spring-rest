package br.com.will.io.cleanarchspringrest.core.ports;

import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import br.com.will.io.cleanarchspringrest.core.domain.Paginado;
import java.util.Optional;

public interface EmpresaRepositoryPort {

  public Optional<Empresa> salvarAtualizar(Empresa empresa);
  public Optional<Empresa> buscarPorDocumento(long documento);
  public Paginado<Empresa> buscarTodos(int pagina, int quantidade);
  public void excluir(long documento);

}
