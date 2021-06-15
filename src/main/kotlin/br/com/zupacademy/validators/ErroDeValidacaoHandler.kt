package br.com.zupacademy.validators

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.validation.ConstraintViolationException

@Controller
class ErroDeValidacaoHandler {

    @Error(global = true)
    fun handle(request: HttpRequest<*>, e: ConstraintViolationException): HttpResponse<Any> {

        val listaErros = ArrayList<ErroFormulario>()
        e.constraintViolations.forEach { c ->

            val campo = c.propertyPath.last().toString()
            val erro = ErroFormulario(campo = campo, erro = c.message)
            listaErros.add(erro)
        }
        return HttpResponse.badRequest(listaErros)
    }
}