package br.com.will.io.cleanarchspringrest.core.ports;

import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import br.com.will.io.cleanarchspringrest.core.domain.Paginado;
import java.util.Optional;

public interface EmpresaRepositoryPort {

  Optional<Empresa> novaEmpresa(Empresa empresa);
  Optional<Empresa> atualizarEmpresa(Empresa empresa);
  Optional<Empresa> buscarPorDocumento(long documento);
  Paginado<Empresa> buscarTodos(int pagina, int quantidade);
  Optional<Empresa> excluir(long documento);

}
