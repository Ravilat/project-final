package com.javarush.jira.common.internal.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;


@Component
@AllArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {


    private final List<HandlerExceptionResolver> resolvers;
    private final RequestMappingHandlerMapping mapping;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {

        try {
            HandlerExecutionChain handler = mapping.getHandler(request);

            for (HandlerExceptionResolver resolver : resolvers) {
                // вызываем resolveException; если обработка прошла, прерываем цикл
                if (resolver.resolveException(request, response,
                        handler == null ? null : handler.getHandler(), authException) != null) {
                    break;
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
