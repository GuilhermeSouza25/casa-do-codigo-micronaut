package br.com.zupacademy.clientes

import br.com.zupacademy.validators.AfterDefaultOrder
import br.com.zupacademy.validators.Documento
import br.com.zupacademy.validators.PaisPossuiEstadoValidator
import br.com.zupacademy.validators.UniqueValue
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.Validated
import javax.persistence.EntityManager
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
open class ClienteRequest(

    @field:NotBlank @field:Email
    @field:UniqueValue(domainClass = Cliente::class, fieldName = "email")
    val email: String,

    @field:NotBlank
    val nome: String,

    @field:NotBlank
    val sobrenome: String,

    @field:NotBlank @field:Documento
    @field:UniqueValue(domainClass = Cliente::class, fieldName = "documento")
    val documento: String,

    @field:NotBlank
    val telefone: String,

    @field:Valid
    @field:NotNull
    open val endereco: EnderecoRequest,

) {
    override fun toString(): String {
        return "ClienteRequest(" +
                "email='$email', " +
                "nome='$nome', " +
                "sobrenome='$sobrenome', " +
                "documento='$documento', " +
                "endereco=$endereco, " +
                "telefone='$telefone'" +
                ")"
    }

    fun toModel(manager: EntityManager): Cliente {

        return Cliente(email, nome, sobrenome, documento, telefone, endereco.toModel(manager))
    }
}