package com.boticario.cashback.controller

import com.boticario.cashback.client.ApiExternaCashback
import com.boticario.cashback.controller.dto.LoginDTOObterToken
import com.boticario.cashback.controller.dto.RevendedorDTO
import com.boticario.cashback.controller.dto.TokenModel
import com.boticario.cashback.service.RevendedorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/revendedores")
class RevendedorController(
    val service : RevendedorService
    )
{

    @PostMapping
    fun cadastrarRevendedor(@RequestBody @Valid revendedorDTO : RevendedorDTO)  = service.salvar(revendedorDTO)

    @PostMapping("/auth")
    fun fazerLogin(@RequestBody @Valid form : LoginDTOObterToken)  : ResponseEntity<TokenModel> = service.fazerLogin(form)

    @GetMapping
    fun visualizarCashback() : String = service.visualizarCashback()
}