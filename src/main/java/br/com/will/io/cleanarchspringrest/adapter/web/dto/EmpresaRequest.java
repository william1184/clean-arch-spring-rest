package br.com.will.io.cleanarchspringrest.adapter.web.dto;

import br.com.will.io.cleanarchspringrest.adapter.web.dto.common.EmpresaDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@SuperBuilder
@Getter
public class EmpresaRequest extends EmpresaDto {


}
