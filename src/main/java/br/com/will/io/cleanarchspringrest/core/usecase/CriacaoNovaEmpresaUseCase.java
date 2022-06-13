package br.com.will.io.cleanarchspringrest.core.usecase;

import br.com.will.io.cleanarchspringrest.core.ports.EmpresaRepositoryPort;
import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CriacaoNovaEmpresaUseCase {

    private final EmpresaRepositoryPort empresaRepositoryPort;

    public Optional<Empresa> execute(Empresa novaEmpresa){
        log.debug("INICIO - CriacaoNovaEmpresaUseCase {}", novaEmpresa);

        var empresaPersistida = empresaRepositoryPort.salvarAtualizar(novaEmpresa);

        log.debug("FIM - CriacaoNovaEmpresaUseCase {}", empresaPersistida);
        return empresaPersistida;
    }
}
