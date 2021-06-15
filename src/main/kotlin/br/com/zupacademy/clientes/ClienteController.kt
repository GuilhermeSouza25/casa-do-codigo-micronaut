package br.com.zupacademy.clientes

import br.com.zupacademy.validators.ValidationOrder
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.ConstraintViolationException
import javax.validation.Validator

@Controller("/clientes")
class ClienteController(
    val validator: Validator,
    val manager: EntityManager
) {

    @Post
    @Transactional
    open fun cadastra(@Body request: ClienteRequest) {

        val violations = validator.validate(request, ValidationOrder::class.java)
        if(violations.isNotEmpty()) throw ConstraintViolationException(violations)

        val cliente = request.toModel(manager)
        manager.persist(cliente)
    }
}