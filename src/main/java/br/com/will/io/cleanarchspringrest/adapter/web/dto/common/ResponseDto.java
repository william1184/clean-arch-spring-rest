package br.com.will.io.cleanarchspringrest.adapter.web.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
public class ResponseDto {

  private final Object data;

  @JsonInclude(Include.NON_NULL)
  private final Paginacao pagination;

}
