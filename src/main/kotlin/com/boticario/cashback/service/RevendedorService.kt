package com.boticario.cashback.service

import com.boticario.cashback.client.ApiExternaCashback
import com.boticario.cashback.controller.dto.LoginDTOObterToken
import com.boticario.cashback.controller.dto.RevendedorDTO
import com.boticario.cashback.controller.dto.TokenModel
import com.boticario.cashback.exception.ObjetoErro
import com.boticario.cashback.exception.RegraNegocioException
import com.boticario.cashback.repository.RevendedorRepository
import com.boticario.cashback.security.TokenService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service

@Service
class RevendedorService(
    val repository: RevendedorRepository,
    val apiExterna: ApiExternaCashback,
    val tokenService: TokenService,
    val authManager: AuthenticationManager
    )
{
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun salvar(revendedorDTO: RevendedorDTO) {
        logger.info("Processando criação de novo revendedor. Payload: ${revendedorDTO.toString()}")
        if (!existePorCpf(revendedorDTO.cpf)) {
            repository.save(revendedorDTO.toDomain())
        }
    }

    private fun existePorCpf(cpf: String) : Boolean {
        val revendedorOptional = repository.findByCpf(cpf)
        logger.info("Existe um revendedor com este cpf no nosso sistema: " + revendedorOptional.isPresent)
        if (!revendedorOptional.isPresent) {
            return false
        } else {
            throw RegraNegocioException(ObjetoErro("cpf", "Já existe um revendedor cadastrado com esse CPF"))
        }
    }

    fun visualizarCashback(): String {
        logger.info("Processando comunicação com sistema externo para visualização de cashbacks.")
        val token : String = "ZXPURQOARHiMc6Y0flhRC1LVlZQVFRnm"
        val obterValoresCashBack = apiExterna.obterValoresCashBack(token)
        logger.info("Solicitação na apiExterna retornou $obterValoresCashBack")
        return "Seu valor total em cashback acumulado é: ${obterValoresCashBack.body.credit}."
    }

    fun fazerLogin(form: LoginDTOObterToken): ResponseEntity<TokenModel> {
        logger.info("Processando autenticação do usuário ${form.email}.")
        val dadosLogin : UsernamePasswordAuthenticationToken = form.converter()
        try {
            val authentication : Authentication = authManager.authenticate(dadosLogin)
            val token : String = tokenService.gerarToken(authentication)
            return ResponseEntity.ok(TokenModel(token, "Bearer"))
            logger.info("Geração do Token Ocorreu com sucesso")
        }
        catch(e : AuthenticationException) {
            logger.warn("Houve uma falha na geração do token")
            return ResponseEntity.badRequest().build();
        }
    }
}