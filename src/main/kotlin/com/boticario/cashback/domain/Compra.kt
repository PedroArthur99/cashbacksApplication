package com.boticario.cashback.domain

import com.boticario.cashback.domain.enum.StatusCompraEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Compra(

    @Id
    val id: String? = null,
    val valor: Double,
    val data: LocalDateTime? = LocalDateTime.now(),
    val cpfRevendedor: String,
    var porcentagemCashbackAplicado: Int? = null,
    var valorCashbackGerado: Double? = null,
    var status: StatusCompraEnum? = null

) {
}