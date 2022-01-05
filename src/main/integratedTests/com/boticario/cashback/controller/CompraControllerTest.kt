package com.boticario.cashback.controller

import com.boticario.cashback.controller.dto.CompraDto
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
internal class CompraControllerTest {

    @Autowired
    val mockMvc: MockMvc? = null

    @Test
    @DisplayName("Cadastro de nova compra com sucesso")
    fun cadastrarCompraComSucesso() {
        val compra: CompraDto = CompraDto(null, 1450.4, null, "01232121321")

        mockMvc?.perform(MockMvcRequestBuilders.post("/compras").
            contentType(MediaType.APPLICATION_JSON))?.
//            content(Json.toJson(compra)))?.
        andExpect(MockMvcResultMatchers.status().isOk)
    }

}
