package br.com.will.io.cleanarchspringrest.core.usecase;

import br.com.will.io.cleanarchspringrest.core.ports.EmpresaRepositoryPort;
import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import br.com.will.io.cleanarchspringrest.core.exceptions.EmpresaNaoEncontradaException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class AtualizarEmpresaUseCase {

    private final EmpresaRepositoryPort empresaRepositoryPort;

    public Optional<Empresa> execute(String documento, Empresa alteracaoEmpresa){
        log.debug("INICIO - Atualizar empresa usecase {}", alteracaoEmpresa);

        var existente = empresaRepositoryPort.buscarPorDocumento(Long.parseLong(documento))
            .orElseThrow(EmpresaNaoEncontradaException::new);

        var empresaAlterada = Empresa
            .builder()
            .nomeFantasia(alteracaoEmpresa.getNomeFantasia())
            .nome(alteracaoEmpresa.getNome())
            .cnpj(existente.getCnpj())
            .dataFundacao(alteracaoEmpresa.getDataFundacao())
            .build();

        var empresaAtualizada = empresaRepositoryPort.salvarAtualizar(empresaAlterada);

        log.debug("FIM - Atualizar empresa usecase {}", empresaAtualizada);
        return empresaAtualizada;
    }
}
