package br.com.will.io.cleanarchspringrest.core.usecase;

import br.com.will.io.cleanarchspringrest.core.exceptions.EmpresaJaExisteException;
import br.com.will.io.cleanarchspringrest.core.ports.EmpresaRepositoryPort;
import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class NovaEmpresaUseCase {

    private final EmpresaRepositoryPort empresaRepositoryPort;

    public Optional<Empresa> execute(Empresa novaEmpresa){
        log.debug("INICIO - CriacaoNovaEmpresaUseCase {}", novaEmpresa);

        var doc = novaEmpresa.getCnpj().getNumeros();
        if(empresaRepositoryPort.buscarPorDocumento(doc).isPresent()){
            throw new EmpresaJaExisteException();
        }

        var empresaPersistida = empresaRepositoryPort.novaEmpresa(novaEmpresa);

        log.debug("FIM - CriacaoNovaEmpresaUseCase {}", empresaPersistida);
        return empresaPersistida;
    }
}
