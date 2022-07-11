package br.com.will.io.cleanarchspringrest.adapter.web.config.exceptions;

import br.com.will.io.cleanarchspringrest.adapter.web.config.exceptions.response.ErrorResponse;
import br.com.will.io.cleanarchspringrest.adapter.web.config.exceptions.response.ErrorResponse.CampoError;
import br.com.will.io.cleanarchspringrest.core.exceptions.EmpresaNaoEncontradaException;
import br.com.will.io.cleanarchspringrest.core.utils.TextUtils;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler  {

    public static final String MENSAGEM_CAMPOS_INVALIDOS = "Campos invalidos";

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex, HttpHeaders headers,
        HttpStatus status, WebRequest request) {

        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse
                    .builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .mensagem(MENSAGEM_CAMPOS_INVALIDOS)
                    .campos(
                        List.of(
                            CampoError
                                .builder()
                                .campo(TextUtils.toSnakeCase(ex.getParameterName()))
                                .mensagem("O campo de parametro não está preenchido")
                                .build()
                        )
                    )
                    .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        log.error("Erro de campos invalidos: {}", ex.getClass());

        var campos = new ArrayList<CampoError>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            campos.add(
                CampoError
                    .builder()
                    .campo(TextUtils.toSnakeCase(error.getField()))
                    .mensagem(error.getDefaultMessage())
                    .valor(error.getRejectedValue())
                    .build()
            );
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            campos.add(
                CampoError
                    .builder()
                    .campo(TextUtils.toSnakeCase(error.getObjectName()))
                    .mensagem(error.getDefaultMessage())
                    .build()
            );
        }

        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse
                    .builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .mensagem(MENSAGEM_CAMPOS_INVALIDOS)
                    .campos(campos)
                    .build());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
        MethodArgumentTypeMismatchException ex, WebRequest request) {
        var name = Optional.ofNullable(ex.getRequiredType()).
            map(Class::getName)
            .orElse("NAME_NOT_FOUND");

        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse
                    .builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .mensagem(MENSAGEM_CAMPOS_INVALIDOS)
                    .campos(List.of(
                        CampoError
                            .builder()
                            .campo(TextUtils.toSnakeCase(ex.getName()))
                            .mensagem(
                                MessageFormat.format(
                                    "O campo deveria ser do tipo {0}",  name
                                )
                            )
                            .build()
                    ))
                    .build());
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
        ConstraintViolationException ex, WebRequest request) {
        var campos = new ArrayList<CampoError>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            var path = violation.getPropertyPath().toString();
            var campo = path.split("\\.")[1];

            campos.add(
                CampoError
                    .builder()
                    .campo(TextUtils.toSnakeCase(campo))
                    .mensagem(violation.getMessage())
                    .build()
            );
        }

        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse
                    .builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .mensagem(MENSAGEM_CAMPOS_INVALIDOS)
                    .campos(campos)
                    .build());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handlerGenerico(Exception ex) {

        log.error("Erro genérico da aplicação: {}", ex.getClass());

        return ResponseEntity
                .internalServerError()
                .body(
                        ErrorResponse
                                .builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .mensagem("Erro interno")
                                .build());
    }

    @ExceptionHandler({ EmpresaNaoEncontradaException.class })
    public ResponseEntity<Object> handlerEmpresaNaoEncontradaException(EmpresaNaoEncontradaException ex) {

        log.warn("Empresa nao encontrada");

        return ResponseEntity
                .internalServerError()
                .body(
                        ErrorResponse
                                .builder()
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .mensagem(ex.getMessage())
                                .build());
    }


}