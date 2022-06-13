package br.com.will.io.cleanarchspringrest.config.spring;

import br.com.will.io.cleanarchspringrest.core.ports.EmpresaRepositoryPort;
import br.com.will.io.cleanarchspringrest.core.usecase.AtualizarEmpresaUseCase;
import br.com.will.io.cleanarchspringrest.core.usecase.BuscarEmpresaPorDocumentoUseCase;
import br.com.will.io.cleanarchspringrest.core.usecase.CriacaoNovaEmpresaUseCase;
import br.com.will.io.cleanarchspringrest.core.usecase.ExcluirEmpresaUseCase;
import br.com.will.io.cleanarchspringrest.core.usecase.ListarEmpresasUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeanConfig {

    @Bean
    CriacaoNovaEmpresaUseCase criacaoNovaEmpresaUseCase(EmpresaRepositoryPort empresaRepositoryPort){
        return new CriacaoNovaEmpresaUseCase(empresaRepositoryPort);
    }

    @Bean
    AtualizarEmpresaUseCase atualizarEmpresaUseCase(EmpresaRepositoryPort empresaRepositoryPort){
        return new AtualizarEmpresaUseCase(empresaRepositoryPort);
    }

    @Bean
    BuscarEmpresaPorDocumentoUseCase buscarEmpresaPorDocumentoUseCase(
        EmpresaRepositoryPort empresaRepositoryPort){
        return new BuscarEmpresaPorDocumentoUseCase(empresaRepositoryPort);
    }

    @Bean
    ListarEmpresasUseCase listarEmpresasUseCase(EmpresaRepositoryPort empresaRepositoryPort){
        return new ListarEmpresasUseCase(empresaRepositoryPort);
    }

    @Bean
    ExcluirEmpresaUseCase excluirEmpresaUseCase(EmpresaRepositoryPort empresaRepositoryPort){
        return new ExcluirEmpresaUseCase(empresaRepositoryPort);
    }


}
