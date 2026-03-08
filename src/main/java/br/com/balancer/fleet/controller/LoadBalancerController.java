package br.com.balancer.fleet.controller;

import br.com.balancer.fleet.service.LoadBalancerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Load Balancer Controller that proxies requests to backend servers.
 */
@RestController
public class LoadBalancerController {

    private final LoadBalancerService loadBalancerService;

    public LoadBalancerController(LoadBalancerService loadBalancerService) {
        this.loadBalancerService = loadBalancerService;
    }

    /**
     * Proxies all incoming requests to the next available server.
     *
     * @param httpServletRequest the incoming HTTP request
     * @return response from the target server
     */
    @RequestMapping("/**")
    public ResponseEntity<String> proxy(HttpServletRequest httpServletRequest) {

        var response = loadBalancerService.proxyRequest(httpServletRequest);
        return ResponseEntity.ok(response);
    }

}
