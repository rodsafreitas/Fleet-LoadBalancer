package br.com.balancer.fleet.config;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Service responsible for loading configuration files from classpath.
 */
@Service
public class ConfigLoader {

    private final ResourceLoader resourceLoader;

    public ConfigLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String loadConfig() {
        Resource resource = resourceLoader.getResource("classpath:server.conf");

        if (!resource.exists()) {
            throw new RuntimeException("Arquivo server.conf não encontrado no classpath");
        }

        try {
            return new String(resource.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar server.conf: " + e.getMessage(), e);
        }
    }
}