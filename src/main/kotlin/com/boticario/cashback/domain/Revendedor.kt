package com.boticario.cashback.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document
data class Revendedor(

    @Id
    val id: String? = null,
    val nomeCompleto: String,
    val cpf: String,
    val login: String,
    val senha: String,
    val perfis: MutableList<RevendedorPerfil> = mutableListOf<RevendedorPerfil>()

) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return perfis
    }

    override fun getPassword(): String {
        return senha
    }

    override fun getUsername(): String {
        return login
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
