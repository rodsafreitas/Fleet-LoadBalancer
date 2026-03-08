package br.com.balancer.fleet.strategy;

public enum LoadBalancingAlgorithm {
    /** Simple round-robin distribution across all servers */
    ROUND_ROBIN,

    /** Weighted round-robin distribution based on server weights */
    WEIGHT_ROBIN
}
