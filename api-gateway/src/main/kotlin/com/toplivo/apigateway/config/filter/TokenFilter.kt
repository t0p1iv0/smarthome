package com.toplivo.apigateway.config.filter

import com.toplivo.apigateway.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class TokenFilter(
    private val tokenService: TokenService,
    private val handlerExceptionResolver: HandlerExceptionResolver
) : OncePerRequestFilter() {

        private val log = KotlinLogging.logger {  }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {

            request.getHeader("X-Access-Token")
                ?.let {

                    val token = tokenService.parseAndValidate(it)
                    tokenService.setContext(token)

                    log.info { "Received request with uri: ${request.method} ${request.requestURI} and access token $token" }
                }
                ?: log.info { "Received request with uri: ${request.method} ${request.requestURI} and without token" }

            filterChain.doFilter(request, response)

            tokenService.removeContext()

        } catch (ex: Exception) {
            handlerExceptionResolver.resolveException(request, response, null, ex)
        }
    }
}