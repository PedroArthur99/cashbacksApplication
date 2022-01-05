package com.boticario.cashback.controller.dto

import com.boticario.cashback.domain.Compra
import com.boticario.cashback.domain.enum.StatusCompraEnum
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CompraDto(

    val id: String? = null,

    @field:NotNull
    val valor: Double,

    @field:NotNull
    val data: LocalDateTime? = LocalDateTime.now(),

    @field:NotBlank
    val cpfRevendedor: String,

    val porcentagemCashbackAplicado: Int? = null,
    val valorCashbackGerado: Double? = null,
    val status: StatusCompraEnum? = null
) {

    constructor (compra: Compra) : this(compra.id,
        compra.valor,
        compra.data,
        compra.cpfRevendedor,
        compra.porcentagemCashbackAplicado,
        compra.valorCashbackGerado,
        compra.status)

    fun toDomain() : Compra = Compra(null, valor, data, cpfRevendedor, porcentagemCashbackAplicado, valorCashbackGerado, status)

    override fun toString(): String {
        return "valor: $valor / data: $data / cpfRevendedor: $cpfRevendedor"
    }

}
