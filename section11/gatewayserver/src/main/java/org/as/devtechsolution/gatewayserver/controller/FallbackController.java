package org.as.devtechsolution.gatewayserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Aditya Srivastva
 */
@RestController
public class FallbackController {

    @GetMapping("/contactSupport")
    public Mono<String> fallback() {
        return Mono.just("An error occurred. Please try after some time or contact support team!!!");
    }

}
