package com.boticario.cashback.controller.dto

import com.boticario.cashback.domain.Revendedor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.validation.constraints.NotBlank

class RevendedorDTO(

    @field:NotBlank
    val nomeCompleto: String,

    @field:NotBlank
    val cpf: String,

    @field:NotBlank
    val login: String,

    @field:NotBlank
    val senha: String
) {

    fun toDomain() : Revendedor = Revendedor(null, nomeCompleto, cpf, login, BCryptPasswordEncoder().encode(senha))

    override fun toString(): String {
        return "nomeCompleto: $nomeCompleto / cpf: $cpf / login: $login / senha: ${BCryptPasswordEncoder().encode(senha)}"
    }

}
