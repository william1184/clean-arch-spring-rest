package br.com.will.io.cleanarchspringrest.core.usecase;

import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import br.com.will.io.cleanarchspringrest.core.ports.EmpresaRepositoryPort;
import br.com.will.io.cleanarchspringrest.core.domain.Paginado;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ListarEmpresasUseCase {

    private final EmpresaRepositoryPort empresaRepositoryPort;

    public Paginado<Empresa> execute(String page, String perPage){
        log.debug("INICIO - ListarEmpresasUseCase");

        var pageInt = Integer.parseInt(page);
        var perPageInt = Integer.parseInt(perPage);

        var todos = empresaRepositoryPort.buscarTodos(pageInt, perPageInt);
        log.debug("INICIO - ListarEmpresasUseCase {}", todos);

        return todos;
    }

}
