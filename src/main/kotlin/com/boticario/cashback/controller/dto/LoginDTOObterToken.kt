package com.boticario.cashback.controller.dto

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class LoginDTOObterToken(
    val email : String,
    val senha : String
) {

    fun converter() : UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, senha)
    }

}
