package com.boticario.cashback.security

import com.boticario.cashback.domain.Revendedor
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.lang.Exception
import java.lang.Long
import java.util.*

@Service
class TokenService {

    @Value("\${cashback.jwt.expiration}")
    val expiration : String? = ""

    @Value("\${cashback.jwt.secret}")
    val secret : String? = ""

    fun gerarToken(authentication : Authentication) : String {
        val usuarioLogado : Revendedor = authentication.principal as Revendedor
        val hoje = Date()
        val dataExpiracao = Date(hoje.time + Long.parseLong(expiration))
        return Jwts.builder().
                setIssuer(usuarioLogado.id.toString()).
                setSubject(usuarioLogado.id.toString()).
                setIssuedAt(hoje).
                setExpiration(dataExpiracao).
                signWith(SignatureAlgorithm.HS256, secret).
                compact()
    }

    fun isValid(token : String) : Boolean {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return true
        }
        catch (e : Exception) {
            return false
        }
    }

    fun getIdUsuario(token : String) : String  {
        val claims : Claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
        return claims.subject
    }

}
