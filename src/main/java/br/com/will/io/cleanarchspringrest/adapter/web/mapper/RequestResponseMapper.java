package br.com.will.io.cleanarchspringrest.adapter.web.mapper;

import br.com.will.io.cleanarchspringrest.adapter.web.dto.EmpresaRequest;
import br.com.will.io.cleanarchspringrest.adapter.web.dto.common.EmpresaDto;
import br.com.will.io.cleanarchspringrest.adapter.web.dto.common.Paginacao;
import br.com.will.io.cleanarchspringrest.adapter.web.dto.common.ResponseDto;
import br.com.will.io.cleanarchspringrest.core.domain.Cnpj;
import br.com.will.io.cleanarchspringrest.core.domain.DataFundacao;
import br.com.will.io.cleanarchspringrest.core.domain.Paginado;
import br.com.will.io.cleanarchspringrest.core.domain.Empresa;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class RequestResponseMapper {

    public Empresa toDomain(EmpresaRequest request){
        return Empresa
                .builder()
                .cnpj(
                    Cnpj
                        .builder()
                        .numeros(Long.valueOf(StringUtils.getDigits(request.getCnpj())))
                        .build())
                .nome(request.getNomeEmpresa())
                .nomeFantasia(request.getNomeFantasia())
                .dataFundacao(
                    DataFundacao
                        .builder()
                        .data(LocalDate.parse(request.getDataFundacao(), DateTimeFormatter.ISO_LOCAL_DATE))
                        .build()
                )
                .build();
    }

    private EmpresaDto toResponseDto(Empresa domain){
        return EmpresaRequest
            .builder()
            .cnpj(domain.getCnpj().getDocumento())
            .nomeEmpresa(domain.getNome())
            .nomeFantasia(domain.getNomeFantasia())
            .dataFundacao(domain.getDataFundacao().getDataFormatadaString())
            .build();
    }

    public ResponseDto toResponse(Empresa domain){
        return ResponseDto
            .builder()
            .data(toResponseDto(domain))
            .build();
    }

    public ResponseDto toResponse(Paginado<Empresa> paginado, String page, String perPage){
        return ResponseDto
            .builder()
            .data(paginado.getItems().stream().map(this::toResponseDto).collect(Collectors.toList()))
            .pagination(
                Paginacao
                    .builder()
                    .page(Integer.parseInt(page))
                    .perPage(Integer.parseInt(perPage))
                    .totalPages(paginado.getTotalPaginas())
                    .totalItems(paginado.getItensTotais())
                    .build()
            )
            .build();
    }

}
