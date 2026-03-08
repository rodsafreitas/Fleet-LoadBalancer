package br.com.balancer.fleet.service;

import br.com.balancer.fleet.strategy.LoadBalancingStrategy;
import br.com.balancer.fleet.strategy.StrategyFactory;
import org.springframework.stereotype.Service;

@Service
public class LoadBalancerService {

    private final LoadBalancingStrategy loadBalancingStrategy;

    /**
     * Creates LoadBalancerService with appropriate strategy based on server configuration.
     */
    public LoadBalancerService(AddressService addressService) {
        var servers = addressService.addresses();
        this.loadBalancingStrategy = StrategyFactory.create(servers);
    }

    /**
     * Gets the next server to handle a request.
     */
    public String getNextService() {
        return loadBalancingStrategy.getNextService();
    }
}
