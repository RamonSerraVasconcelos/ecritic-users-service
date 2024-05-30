package com.ecritic.ecritic_users_service.entrypoint.validation;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static java.util.Objects.isNull;

@Component
@Slf4j
public class ApplicationRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestHeaders headers = getHeaders(request);

        response.setHeader("X-Request-Id", headers.getRequestId());

        if (isEndpointOpen(request)) {
            logRequest(request, headers);
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (isNull(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            String userId = TokenUtils.getUserIdFromToken(token);
            String userRole = TokenUtils.getUserRoleFromToken(token);

            request.setAttribute("userId", userId);
            request.setAttribute("role", userRole);
        } catch (Exception e) {
            log.error("Error retrieving access token", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } finally {
            logRequest(request, headers);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isEndpointOpen(HttpServletRequest request) {
        String excludeUrls = getFilterConfig().getInitParameter("excludeUrls");
        String[] excludeUrlArray = excludeUrls.split(",");
        AntPathMatcher pathMatcher = new AntPathMatcher();

        for (String excludeUrl : excludeUrlArray) {
            String trimmedExcludeUrl = excludeUrl.trim();

            if (pathMatcher.match(trimmedExcludeUrl, request.getRequestURI())) {
                return true;
            }
        }

        return false;
    }

    private RequestHeaders getHeaders(HttpServletRequest request) {
        String requestId = request.getHeader("X-Request-Id");

        if (isNull(requestId)) {
            requestId = UUID.randomUUID().toString();
        }

        return RequestHeaders.builder()
                .requestId(requestId)
                .build();
    }

    private void logRequest(HttpServletRequest request, RequestHeaders headers) {
        log.info("Logging request INFO - URI: [{}], Headers: [{}]", request.getRequestURI(), headers.toString());
    }
}
