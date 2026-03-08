package br.com.balancer.fleet.strategy;

/**
 * Interface for load balancing strategies.
 * Implementations should provide thread-safe server selection algorithms.
 */
public interface LoadBalancingStrategy {

    /**
     * Gets the next server URL to handle a request.
     *
     * @return URL of the selected server
     */
    String getNextService();
}
