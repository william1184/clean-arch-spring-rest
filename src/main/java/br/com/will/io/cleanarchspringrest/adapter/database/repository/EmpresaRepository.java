package br.com.will.io.cleanarchspringrest.adapter.database.repository;

import br.com.will.io.cleanarchspringrest.adapter.database.entity.EmpresaEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmpresaRepository extends PagingAndSortingRepository<EmpresaEntity, Long > {
}
