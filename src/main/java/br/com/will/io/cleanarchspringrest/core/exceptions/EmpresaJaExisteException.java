package br.com.will.io.cleanarchspringrest.core.exceptions;

public class EmpresaJaExisteException extends RuntimeException {

  public EmpresaJaExisteException(){
    super("Empresa já existe");
  }

  public EmpresaJaExisteException(String message) {
    super(message);
  }
}
