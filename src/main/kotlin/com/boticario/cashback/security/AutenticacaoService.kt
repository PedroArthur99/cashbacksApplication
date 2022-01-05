package com.boticario.cashback.security

import com.boticario.cashback.domain.Revendedor
import com.boticario.cashback.repository.RevendedorRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class AutenticacaoService(
    val revendedorRepository: RevendedorRepository
    ) : UserDetailsService
{

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username != null) {
            val revendedor : Optional<Revendedor> = revendedorRepository.findByLogin(username)
            if(revendedor.isPresent()) {
                return revendedor.get()
            }
        }
        throw UsernameNotFoundException("Dados inválidos!")
    }
}