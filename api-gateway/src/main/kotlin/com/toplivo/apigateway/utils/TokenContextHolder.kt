package com.toplivo.apigateway.utils

class TokenContextHolder {
    companion object {
        private val context = ThreadLocal<String>()

        fun get(): String? = context.get()

        fun set(value: String) = context.set(value)

        fun remove() = context.remove()
    }
}