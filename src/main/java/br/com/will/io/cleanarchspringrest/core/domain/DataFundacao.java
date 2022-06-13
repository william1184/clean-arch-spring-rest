package br.com.will.io.cleanarchspringrest.core.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DataFundacao {
  private LocalDate data;

  public boolean isDataValida(){
    return !this.data.isAfter(LocalDate.now());
  }

  public String getDataFormatadaString(){
    return data.format(DateTimeFormatter.ISO_LOCAL_DATE);
  }
}
