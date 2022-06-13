package br.com.will.io.cleanarchspringrest.core.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Paginado<T> {

  private List<T> items;

  private final int pagina;
  private final int itensPorPagina;
  private final long itensTotais;
  private final int totalPaginas;

}
