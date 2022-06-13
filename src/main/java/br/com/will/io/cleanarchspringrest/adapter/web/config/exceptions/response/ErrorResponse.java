package br.com.will.io.cleanarchspringrest.adapter.web.config.exceptions.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;

import java.util.List;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

    @Builder
    @Getter
    public static class CampoError {
        private final String campo;
        private final String mensagem;
        @JsonInclude(Include.NON_NULL)
        private final Object valor;
    }

    private final int statusCode;
    private final String mensagem;
    @JsonInclude(Include.NON_NULL)
    private final List<CampoError> campos;
}
