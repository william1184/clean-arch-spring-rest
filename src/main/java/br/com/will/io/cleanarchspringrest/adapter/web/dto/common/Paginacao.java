package br.com.will.io.cleanarchspringrest.adapter.web.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Paginacao {

  private final long page;
  @JsonProperty("per_page")
  private final long perPage;
  @JsonProperty("total_pages")
  private final long totalPages;
  @JsonProperty("total_items")
  private final long totalItems;

}
