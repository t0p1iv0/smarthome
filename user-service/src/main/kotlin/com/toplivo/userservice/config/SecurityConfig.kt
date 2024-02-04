package com.toplivo.userservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun disableAuth(http: HttpSecurity) =
        http.authorizeHttpRequests {
                it.anyRequest()
                    .permitAll()
            }
            .csrf { it.disable() }
            .build()

    @Bean
    fun passwordEncoder() =  BCryptPasswordEncoder()
}