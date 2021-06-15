package br.com.zupacademy.categoria

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/categorias")
class CategoriaController(
    val manager: EntityManager
) {

    @Post
    @Transactional
    fun cadastrar(@Body @Valid request: CategoriaRequest): HttpResponse<Any> {

        val categoria = request.toModel()
        manager.persist(categoria)

        return HttpResponse.ok()
    }
}