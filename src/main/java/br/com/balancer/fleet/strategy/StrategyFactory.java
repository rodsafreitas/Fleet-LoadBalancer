package br.com.balancer.fleet.strategy;

import br.com.balancer.fleet.model.Address;

import java.util.List;

/**
 * Factory for creating appropriate load balancing strategies.
 * Automatically selects strategy based on server configuration.
 */
public class StrategyFactory {

    /**
     * Creates appropriate load balancing strategy based on server weights.
     * Uses WeightedRoundRobin if any server has weight > 1
     *  in this case the weighted was configured
     *  otherwise uses RoundRobin.
     *
     * @param addressList list of server addresses
     * @return appropriate load balancing strategy
     */
    public static LoadBalancingStrategy create(List<Address> addressList) {
        if (addressList == null || addressList.isEmpty()) {
            throw new IllegalArgumentException("Address list cannot be null or empty");
        }

        LoadBalancingAlgorithm algorithm = resolveType(addressList);

        return switch (algorithm) {
            case ROUND_ROBIN -> new RoundRobinStrategy(addressList);
            case WEIGHT_ROBIN -> new WeightedRoundRobinStrategy(addressList);
        };
    }

    /**
     * Determines which algorithm to use based on server weights.
     */
    private static LoadBalancingAlgorithm resolveType(List<Address> addressList) {
        boolean hasWeight = addressList.stream()
                .anyMatch(address -> address.weight() > 1);

        return hasWeight
                ? LoadBalancingAlgorithm.WEIGHT_ROBIN
                : LoadBalancingAlgorithm.ROUND_ROBIN;
    }
}
