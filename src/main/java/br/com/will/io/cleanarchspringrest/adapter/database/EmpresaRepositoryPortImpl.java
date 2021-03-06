package br.com.will.io.cleanarchspringrest.adapter.database;

import br.com.will.io.cleanarchspringrest.adapter.database.entity.EmpresaEntity;
import br.com.will.io.cleanarchspringrest.adapter.database.mapper.EntityDomainMapper;
import br.com.will.io.cleanarchspringrest.adapter.database.repository.EmpresaRepository;
import br.com.will.io.cleanarchspringrest.core.ports.EmpresaRepositoryPort;
import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import br.com.will.io.cleanarchspringrest.core.domain.Paginado;
import br.com.will.io.cleanarchspringrest.core.exceptions.EmpresaNaoEncontradaException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EmpresaRepositoryPortImpl implements EmpresaRepositoryPort {

  private final EmpresaRepository repository;
  private final EntityDomainMapper mapper;

  @Override
  public Optional<Empresa> salvarAtualizar(Empresa empresa) {
    var entidade= mapper.toEntity(empresa);

    var salvo = repository.save(entidade);

    return Optional.of(mapper.toDomain(salvo));
  }

  @Override
  public Optional<Empresa> buscarPorDocumento(long documento) {
    var salvo = repository.findById(documento);

    return Optional.of(mapper.toDomain(salvo.orElseThrow(EmpresaNaoEncontradaException::new)));
  }

  @Override
  public Paginado<Empresa> buscarTodos(int pagina, int quantidade) {

    pagina--;

    var salvo = repository.findAll(PageRequest.of(pagina, quantidade));

    return mapper.toDomain(salvo);
  }

  @Override
  public void excluir(long documento) {
    repository.delete(
        EmpresaEntity
            .builder()
            .cnpj(documento)
            .build()
    );
  }
}
