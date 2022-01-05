package com.boticario.cashback.security

import com.boticario.cashback.domain.Revendedor
import com.boticario.cashback.repository.RevendedorRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AutenticacaoViaTokenFilter(val tokenService: TokenService, val revendedorRepository: RevendedorRepository) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token : String = recuperarToken(request)
        val valido : Boolean = tokenService.isValid(token)
        if (valido) {
            autenticarCliente(token)
        }
        filterChain.doFilter(request, response)
    }

    private fun recuperarToken(request: HttpServletRequest) : String {
        val token : String = if (request.getHeader("Authorization") != null) request.getHeader("Authorization") else "" ;
        if (token == "" || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null.toString()
        }
        return token.substring(7, token.length)

    }

    private fun autenticarCliente(token: String) {
        val idUsuario : String = tokenService.getIdUsuario(token)
        val revendedor : Revendedor = revendedorRepository.findById(idUsuario).get()
        val authentication : UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(revendedor, null, revendedor.authorities)
        SecurityContextHolder.getContext().authentication = authentication

    }

}
