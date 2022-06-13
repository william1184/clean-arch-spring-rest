package br.com.will.io.cleanarchspringrest.core.exceptions;

public class EmpresaNaoEncontradaException extends RuntimeException {

  public EmpresaNaoEncontradaException(){
    super("Empresa não encontrada");
  }

  public EmpresaNaoEncontradaException(String message) {
    super(message);
  }
}
