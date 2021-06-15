package br.com.zupacademy.paises

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.Constraint
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import kotlin.system.measureNanoTime

@Validated
@Controller
class PaisController(
    val manager: EntityManager
) {

    @Post("/paises")
    @Transactional
    fun cadastraPais(@Body @Valid request: PaisRequest): HttpResponse<Any> {

        val pais = request.toModel()
        manager.persist(pais)

        return HttpResponse.ok()
    }

    @Post("/estados")
    @Transactional
    fun cadastraEstado(@Body @Valid request: EstadoRequest): HttpResponse<Any> {

        val estado = request.toModel(manager)
        manager.persist(estado)

        return HttpResponse.ok()
    }
}