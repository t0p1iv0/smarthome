package com.toplivo.deviceservice.config.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RequestFilter(
) : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {  }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

            request.getHeader("token")
                .let {
                    log.info { "Received request with uri: ${request.method} ${request.requestURI} and access token $it" }
                }

            filterChain.doFilter(request, response)

    }

}