package com.boticario.cashback.controller

import com.boticario.cashback.controller.dto.CompraDto
import com.boticario.cashback.domain.Revendedor
import com.boticario.cashback.service.CompraService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/compras")
class CompraController(
    val compraService: CompraService
    )
{

    @PostMapping
    fun salvar(@RequestBody @Valid compraDto: CompraDto) = compraService.salvar(compraDto)

    @GetMapping
    fun listar(@AuthenticationPrincipal revendedor: Revendedor) = compraService.listarCompras(revendedor)
}