package br.com.will.io.cleanarchspringrest.adapter.web;

import br.com.will.io.cleanarchspringrest.adapter.web.dto.EmpresaRequest;
import br.com.will.io.cleanarchspringrest.adapter.web.dto.common.ResponseDto;
import br.com.will.io.cleanarchspringrest.adapter.web.mapper.RequestResponseMapper;
import br.com.will.io.cleanarchspringrest.core.usecase.AtualizarEmpresaUseCase;
import br.com.will.io.cleanarchspringrest.core.usecase.BuscarEmpresaPorDocumentoUseCase;
import br.com.will.io.cleanarchspringrest.core.usecase.NovaEmpresaUseCase;
import br.com.will.io.cleanarchspringrest.core.usecase.ExcluirEmpresaUseCase;
import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import br.com.will.io.cleanarchspringrest.core.usecase.ListarEmpresasUseCase;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EmpresaController {

    private final RequestResponseMapper mapper;
    private final NovaEmpresaUseCase novaEmpresaUseCase;
    private final AtualizarEmpresaUseCase atualizarEmpresaUseCase;
    private final BuscarEmpresaPorDocumentoUseCase buscarEmpresaPorDocumentoUseCase;
    private final ListarEmpresasUseCase listarEmpresasUseCase;
    private final ExcluirEmpresaUseCase excluirEmpresaUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> criarEmpresa(@Validated @RequestBody EmpresaRequest request){
        log.info("INICIO - Criação nova empresa: {}", request);

        var domain = novaEmpresaUseCase.execute(mapper.toDomain(request));

        var response = domain.map(mapper::toResponse).orElseThrow();

        var location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(domain.map(Empresa::getCnpj).orElseThrow())
            .toUri();

        log.info("FIM - Criação nova empresa: {}", response);

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping(path = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> atualizarEmpresa(
        @CNPJ @PathVariable(value = "documento") String documento,
        @Validated @RequestBody EmpresaRequest request
    ){
        log.info("INICIO - Atualiza empresa: {}", request);

        var domain = atualizarEmpresaUseCase.execute(documento, mapper.toDomain(request));

        var response = domain.map(mapper::toResponse).orElseThrow();

        log.info("FIM - Atualiza empresa: {}", response);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> buscarEmpresaPorDocumento(
        @PathVariable(value = "documento") String documento
    ){
        log.info("INICIO - Buscar Empresa por documento: documento : {}", documento);

        var empresasDomain = buscarEmpresaPorDocumentoUseCase.execute(documento);

        var response = empresasDomain.map(mapper::toResponse).orElseThrow();

        log.info("FIM - Buscar Empresa por documento: {}", response);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> listarTodasEmpresas(
         @Min(1) @RequestParam(value = "page", defaultValue = "1") String page,
         @Min(10) @RequestParam(value = "per_page", defaultValue = "10") String perPage
    ){
        log.info("INICIO - Listando empresas: pagina : {}, por_pagina {}", page, perPage);

        var empresasDomain = listarEmpresasUseCase.execute(page, perPage);

        var response = mapper.toResponse(empresasDomain, page, perPage);

        log.info("FIM - Listando empresas: {}", response);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> excluirEmpresa(
        @PathVariable(value = "documento") String documento
    ){
        log.info("INICIO - Exclusao empresa: documento {}", documento);

        var empresaDomain = excluirEmpresaUseCase.execute(documento);

        var response = empresaDomain.map(mapper::toResponse).orElseThrow();

        log.info("FIM - Exclusao empresa: documento");

        return ResponseEntity.ok().body(response);
    }
}
