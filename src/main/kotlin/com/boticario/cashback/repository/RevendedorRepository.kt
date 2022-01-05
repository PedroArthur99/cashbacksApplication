package com.boticario.cashback.repository

import com.boticario.cashback.domain.Revendedor
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface RevendedorRepository : MongoRepository<Revendedor, String> {

    fun findByCpf(cpf: String) : Optional<Revendedor>
    fun findByLogin(login: String) : Optional<Revendedor>
}