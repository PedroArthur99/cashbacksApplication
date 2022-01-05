package com.boticario.cashback.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "sistema-cashbacks", url = "\${feign.client.sistema-cashbacks}")
interface ApiExternaCashback {

    @GetMapping
    fun obterValoresCashBack(@RequestHeader("token") token: String) : ApiExternaResponse
}