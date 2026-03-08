package br.com.balancer.fleet.service;

import br.com.balancer.fleet.strategy.LoadBalancingStrategy;
import br.com.balancer.fleet.strategy.StrategyFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoadBalancerService {

    private final LoadBalancingStrategy loadBalancingStrategy;
    private final RestTemplate restTemplate;

    /**
     * Creates LoadBalancerService with appropriate strategy based on server configuration.
     */
    public LoadBalancerService(AddressService addressService,
                               RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        var servers = addressService.addresses();
        this.loadBalancingStrategy = StrategyFactory.create(servers);
    }

    public String proxyRequest(HttpServletRequest httpServletRequest) {
        String targetServer = loadBalancingStrategy.getNextService();
        String targetUrl = buildTargetUrl(targetServer, httpServletRequest);

        return restTemplate.getForObject(targetUrl, String.class);

    }

    private String buildTargetUrl(String targetServer, HttpServletRequest request) {
        String targetUrl = targetServer + request.getRequestURI();

        String queryString = request.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            targetUrl += "?" + queryString;
        }

        return targetUrl;
    }
}
