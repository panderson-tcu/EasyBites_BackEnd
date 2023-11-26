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
/*
 * This class handles unsuccessful basic authentication.
 * Implement AuthenticationEntryPoint and delegate exception handler to HandlerExceptionResolver
 */
@Component
public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /*
    * here we inject DefaultHandlerExceptionResolver and delegate handler to this resolver.
    * This security exception will now be handled by controller advice with exception handler method.
     */
    private final HandlerExceptionResolver resolver;

    public CustomBasicAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    /** this method is responsible for handling basic authentication.
     * if basic authentication fails, this method will be called
     *
     *
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"Realm\""); // add header and then delegate to the Handler exception resolver
        this.resolver.resolveException(request, response, null, authException); // resolver will resolve this exception so that the exception can be handled by a handler method in Controller advice class

    }
}
