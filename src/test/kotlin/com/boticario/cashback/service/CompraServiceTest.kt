package com.boticario.cashback.service

import com.boticario.cashback.controller.dto.CompraDto
import com.boticario.cashback.domain.Compra
import com.boticario.cashback.domain.Revendedor
import com.boticario.cashback.repository.CompraRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@TestPropertySource(properties = ["spring.mongodb.embedded.version=3.5.5"])
class CompraServiceTest {

    @Autowired
    val compraService: CompraService? = null

    @MockBean
    private val compraRepository: CompraRepository? = null

    private val cpf : String = "70098971409"

    @Before
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(compraRepository?.findAllByCpfRevendedor(cpf)).willReturn(compra())
        BDDMockito.given(compraRepository?.save(compraEntity())).willReturn(compraEntity())
    }

    @Test
    fun buscarCompras() {
        val list: List<CompraDto>? = compraService?.listarCompras(revendedor())
        Assert.assertNotNull(list)
    }

    @Test
    fun salvarCompra() {
        compraService?.salvar(compraDto())
        val list: List<CompraDto>? = compraService?.listarCompras(revendedor())
        Assert.assertNotNull(list)
    }

    private fun compraDto(): CompraDto {
        val compraDto: CompraDto = CompraDto(null, 1243.4, null, cpf, null, null, null)
        return compraDto
    }

    private fun revendedor(): Revendedor {
        val revendedor: Revendedor = Revendedor(null, "Pedro Arthur Barbosa Carvalho", cpf, "pedro@email.com", "senha")
        return revendedor
    }

    private fun compraEntity(): Compra {
        val compra: Compra = Compra(null, 1243.4, null, cpf, null, null, null)
        return compra
    }

    private fun compra(): List<Compra>? {
        val list: List<Compra> = arrayListOf<Compra>(Compra(null, 1243.4, null, cpf, null, null, null))
        return list
    }
}