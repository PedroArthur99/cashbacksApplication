package com.boticario.cashback.service

import com.boticario.cashback.controller.dto.CompraDto
import com.boticario.cashback.domain.Compra
import com.boticario.cashback.domain.Revendedor
import com.boticario.cashback.domain.enum.StatusCompraEnum
import com.boticario.cashback.exception.ObjetoErro
import com.boticario.cashback.exception.RegraNegocioException
import com.boticario.cashback.repository.CompraRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CompraService(
    val compraRepository : CompraRepository
    )
{
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun salvar(compraDto: CompraDto) {
        logger.info("Processando criação de nova compra. Payload: $compraDto")
        val compra : Compra = compraDto.toDomain()
        preencherCampos(compra)
        compraRepository.save(compra)
        logger.info("Uma nova compra foi persistida na base.")
    }

    private fun preencherCampos(compra: Compra): Compra {
        compra.status = if(compra.cpfRevendedor.equals("153.509.460-56")) StatusCompraEnum.APROVADO else StatusCompraEnum.EM_VALIDACAO
        if (compra.valor < 1000) {
            compra.porcentagemCashbackAplicado = 10
            compra.valorCashbackGerado = compra.valor * 0.1
        }
        else if (compra.valor > 1500) {
            compra.porcentagemCashbackAplicado = 20
            compra.valorCashbackGerado = compra.valor * 0.2
        }
        else {
            compra.porcentagemCashbackAplicado = 15
            compra.valorCashbackGerado = compra.valor * 0.15
        }
        return compra
    }

    fun listarCompras(revendedor: Revendedor): List<CompraDto> {
        logger.info("Processando listagem de compras do revendedor ${revendedor.nomeCompleto}")
        val listaCompras: List<Compra> = compraRepository.findAllByCpfRevendedor(revendedor.cpf)
        if (listaCompras.isEmpty()) {
            throw RegraNegocioException(ObjetoErro("Compras", "Você não tem nenhuma compra cadastrada!"))
        }
        var listaComprasDto: List<CompraDto> = listaCompras.map { CompraDto(it) }
        return listaComprasDto
    }
}