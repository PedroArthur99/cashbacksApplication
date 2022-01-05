package com.boticario.cashback.exception

import java.time.LocalDateTime

open class ObjetoErro(

    val campo: String,
    val erro: String?,
    val instanteDoErro: LocalDateTime? = LocalDateTime.now()
)
{


}
