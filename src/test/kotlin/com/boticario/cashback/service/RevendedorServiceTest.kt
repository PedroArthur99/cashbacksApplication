package com.boticario.cashback.service

import com.boticario.cashback.controller.dto.CompraDto
import com.boticario.cashback.controller.dto.LoginDTOObterToken
import com.boticario.cashback.controller.dto.RevendedorDTO
import com.boticario.cashback.controller.dto.TokenModel
import com.boticario.cashback.domain.Compra
import com.boticario.cashback.domain.Revendedor
import com.boticario.cashback.repository.CompraRepository
import com.boticario.cashback.repository.RevendedorRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.ResponseEntity
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import kotlin.jvm.Throws

@RunWith(SpringRunner::class)
@SpringBootTest
@TestPropertySource(properties = ["spring.mongodb.embedded.version=3.5.5"])
internal class RevendedorServiceTest {

    @Autowired
    val revendedorService: RevendedorService? = null

    @MockBean
    private val revendedorRepository: RevendedorRepository? = null

    private val cpf : String = "70098971409"

    @Before
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(revendedorRepository?.findByCpf(cpf)).willReturn(revendedor())
        BDDMockito.given(revendedorRepository?.save(revendedorEntity())).willReturn(revendedorEntity())
    }

    @Test
    @Throws(Exception::class)
    fun testCadastrarRevendedor() {
        val revendedorDTO: RevendedorDTO = RevendedorDTO("Pedro", "01201201212", "pedro@12.com", "asdasdasd")
        revendedorService?.salvar(revendedorDTO)
        Assert.assertNotNull(revendedorRepository?.findByCpf("01201201212"))
    }

    @Test
    @Throws(Exception::class)
    fun testFazerLogin() {
        val revendedor: Revendedor? = revendedorRepository?.save(revendedorEntity())
        if (revendedor != null) {
            val loginForm: LoginDTOObterToken = LoginDTOObterToken(revendedor.login, revendedor.senha)
            val token: ResponseEntity<TokenModel>? = revendedorService?.fazerLogin(loginForm)
            Assert.assertNotNull(token)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testVisualizarCashback() {
        val visualizarCashback = revendedorService?.visualizarCashback()
        Assert.assertNotNull(visualizarCashback?.contains("Seu valor total em cashback acumulado é"))
    }

    private fun revendedorEntity(): Revendedor {
        val revendedor: Revendedor = Revendedor(null, "Pedro Arthur Barbosa Carvalho", cpf, "pedro@email.com", "senha")
        return revendedor
    }

    private fun revendedor(): Optional<Revendedor> {
        val revendedor: Revendedor = Revendedor(null, "Pedro Arthur Barbosa Carvalho", cpf, "pedro@email.com", "senha")
        return Optional.of(revendedor)
    }
}