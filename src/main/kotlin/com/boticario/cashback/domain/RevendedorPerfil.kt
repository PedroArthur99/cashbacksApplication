package com.boticario.cashback.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority

@Document
class RevendedorPerfil(

    @Id
    val id: String? = null,
    val nomePermissao: String

    ) : GrantedAuthority
{
    override fun getAuthority(): String {
        return nomePermissao
    }

}
