package br.com.zupacademy.clientes

import br.com.zupacademy.paises.Pais
import br.com.zupacademy.validators.AfterDefaultOrder
import br.com.zupacademy.validators.ExistisId
import br.com.zupacademy.validators.PaisPossuiEstadoValidator
import br.com.zutbpacademy.pais.Estado
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.Validated
import javax.persistence.EntityManager
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import kotlin.reflect.KClass

@Introspected
@PaisPossuiEstadoValidator(groups =  [AfterDefaultOrder::class])
open class EnderecoRequest(

    @field:NotBlank
    val logradouro: String,

    @field:NotBlank
    val complemento: String,

    @field:NotBlank
    val cidade: String,

    @field:NotNull
    @field:ExistisId(domainClass = Pais::class, fieldName = "id")
    val paisId: Long?,

    @field:ExistisId(domainClass = Estado::class, fieldName = "id")
    val estadoId: Long?,

    @field:NotBlank
    val cep: String
) {
    override fun toString(): String {
        return "EnderecoRequest(" +
                "logradouro='$logradouro', " +
                "complemento='$complemento', " +
                "cidade='$cidade', " +
                "paisId=$paisId, " +
                "estadoId=$estadoId, " +
                "cep='$cep'" +
                ")"
    }

    fun toModel(manager: EntityManager): Endereco {

        val pais = manager.find(Pais::class.java, paisId)
        val estado = manager.find(Estado::class.java, estadoId)

        return Endereco(logradouro, complemento, cidade, cep, pais, estado)
    }
}
