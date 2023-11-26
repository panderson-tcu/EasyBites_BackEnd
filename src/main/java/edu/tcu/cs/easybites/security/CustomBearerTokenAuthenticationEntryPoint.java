package edu.tcu.cs.easybites.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * this class handles unsuccessful JWT Authentication.
 */
@Component
public class CustomBearerTokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /*
     * here we inject DefaultHandlerExceptionResolver and delegate handler to this resolver.
     * This security exception will now be handled by controller advice with exception handler method.
     */
    private final HandlerExceptionResolver resolver;

    public CustomBearerTokenAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        this.resolver.resolveException(request, response, null, authException); // resolver will resolve this exception so that the exception can be handled by a handler method in Controller advice class

    }
}
