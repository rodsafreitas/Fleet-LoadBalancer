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
    private final RestTemplate restTemplate;

    public LoadBalancerController(LoadBalancerService loadBalancerService,
                                  RestTemplate restTemplate) {
        this.loadBalancerService = loadBalancerService;
        this.restTemplate = restTemplate;
    }

    /**
     * Proxies all incoming requests to the next available server.
     *
     * @param httpServletRequest the incoming HTTP request
     * @return response from the target server
     */
    @RequestMapping("/**")
    public ResponseEntity<String> proxy(HttpServletRequest httpServletRequest) {

        String targetServer = loadBalancerService.getNextService();
        String targetUrl = buildTargetUrl(targetServer, httpServletRequest);

        String response = restTemplate.getForObject(targetUrl, String.class);
        return ResponseEntity.ok(response);
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
