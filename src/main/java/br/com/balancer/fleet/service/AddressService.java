package br.com.balancer.fleet.service;

import br.com.balancer.fleet.config.ConfigLoader;
import br.com.balancer.fleet.config.ConfigParser;
import br.com.balancer.fleet.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Load all address from config classes.
 */
@Service
public class AddressService {

    private final ConfigLoader configLoader;

    public AddressService(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    public List<Address> addresses() {
        String config = configLoader.loadConfig();
        return ConfigParser.parseServers(config);
    }

}