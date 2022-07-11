package br.com.will.io.cleanarchspringrest.core.usecase;

import br.com.will.io.cleanarchspringrest.core.exceptions.EmpresaJaExisteException;
import br.com.will.io.cleanarchspringrest.core.exceptions.EmpresaNaoEncontradaException;
import br.com.will.io.cleanarchspringrest.core.ports.EmpresaRepositoryPort;
import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class BuscarEmpresaPorDocumentoUseCase {

    private final EmpresaRepositoryPort empresaRepositoryPort;

    public Optional<Empresa> execute(String documento){
        log.debug("INICIO - BuscarEmpresaPorDocumentoUseCase {}", documento);
        var doc = Long.parseLong(documento);

        var empresa = empresaRepositoryPort.buscarPorDocumento(doc)
            .orElseThrow(EmpresaNaoEncontradaException::new);

        log.debug("FIM - BuscarEmpresaPorDocumentoUseCase {}", empresa);

        return Optional.of(empresa);
    }

}
