package com.boticario.cashback.domain

import com.boticario.cashback.domain.enum.StatusCompraEnum
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@TestPropertySource(properties = ["spring.mongodb.embedded.version=3.5.5"])
internal class StatusCompraEnumTest {

    @Test
    fun testarEnumEmValidacao() {
        val enumValue : String = StatusCompraEnum.EM_VALIDACAO.toString()
        Assert.assertEquals(enumValue, "EM_VALIDACAO")
    }

    @Test
    fun testarEnumAprovado() {
        val enumValue : String = StatusCompraEnum.APROVADO.toString()
        Assert.assertEquals(enumValue, "APROVADO")
    }
}