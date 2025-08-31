package br.com.oficina.adapters.config;

import br.com.oficina.adapters.repositories.InMemoryProdutoRepository;
import br.com.oficina.adapters.repositories.InMemoryServicoRepository;
import br.com.oficina.ports.ProdutoRepository;
import br.com.oficina.ports.ServicoRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "local"})
public class RepositoryConfigDev {

    @Bean
    @Primary
    @ConditionalOnMissingBean(ProdutoRepository.class)
    public ProdutoRepository produtoRepositoryDev() {
        return new InMemoryProdutoRepository();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(ServicoRepository.class)
    public ServicoRepository servicoRepositoryDev() {
        return new InMemoryServicoRepository();
    }
}
