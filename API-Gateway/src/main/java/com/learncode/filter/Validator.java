package com.learncode.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.function.Predicate;

@Component
public class Validator {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/generate-token",
            "/validate-token/{token}"
    );

    public Predicate<ServerHttpRequest> predicate = serverHttpRequest -> {
        serverHttpRequest.getURI().getPath();
        return openApiEndpoints.stream().noneMatch(path -> antPathMatcher.match(path, serverHttpRequest.getURI().getPath()));
    };
}
