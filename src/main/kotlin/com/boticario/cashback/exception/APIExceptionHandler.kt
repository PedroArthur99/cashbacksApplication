package com.boticario.cashback.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class APIExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodNotValid(exception: MethodArgumentNotValidException) : List<ObjetoErro> {
        val dto: MutableList<ObjetoErro> = mutableListOf<ObjetoErro>()
        val fieldErros: List<FieldError> = exception.bindingResult.fieldErrors
        fieldErros.forEach {
            dto.add(ObjetoErro(erro = it.defaultMessage, campo = it.field))
        }
        logger.warn("Erros obtidos da requisição: $dto")
        return dto
    }

    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [RegraNegocioException::class])
    fun handleRegraNegocioException(exception: RegraNegocioException) : ObjetoErro {
        logger.warn(exception.message)
        return exception.objetoErro
    }

    @ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Exception::class])
    fun handleGenericException(exception: Exception) : ObjetoErro {
        logger.warn(exception.message)
        exception.printStackTrace()
        return ObjetoErro("API", "Ocorreu um erro no nosso sistema, por favor, tente novamente mais tarde.")
    }
}