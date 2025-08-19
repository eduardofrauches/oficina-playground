package br.com.oficina.adapters.config;

import br.com.oficina.adapters.repositories.jpa.JpaClienteRepository;
import br.com.oficina.ports.ClienteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ClienteRepository clienteRepository() {
        // Implementacao JPA que voce criou
        return new JpaClienteRepository();
    }
}
