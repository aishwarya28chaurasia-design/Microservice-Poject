package com.learncode.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @GetMapping("/employeeServiceFallback")
    public Mono<String> employeeMethodFallback(){
        return Mono.just("Employee Service is down. Please try again later");
    }

    @GetMapping("/addressServiceFallback")
    public Mono<String> addressMethodFallback(){
        return Mono.just("Address Service is down. Please try again later");
    }
}
