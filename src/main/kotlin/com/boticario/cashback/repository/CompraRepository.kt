package com.boticario.cashback.repository

import com.boticario.cashback.domain.Compra
import org.springframework.data.mongodb.repository.MongoRepository

interface CompraRepository : MongoRepository<Compra, String> {

    fun findAllByCpfRevendedor(cpfRevendedor: String) : List<Compra>
}