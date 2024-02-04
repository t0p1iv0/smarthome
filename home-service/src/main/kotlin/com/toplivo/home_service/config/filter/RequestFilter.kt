package com.toplivo.home_service.config.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class RequestFilter(
    private val handlerExceptionResolver: HandlerExceptionResolver
) : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {  }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            request.getHeader("token")
                ?.let {
                    log.info { "Received request with uri: ${request.method} ${request.requestURI} and access token $it" }
                }

            filterChain.doFilter(request, response)

        } catch (ex: Exception) {
            handlerExceptionResolver.resolveException(request, response, null, ex)
        }

    }

}