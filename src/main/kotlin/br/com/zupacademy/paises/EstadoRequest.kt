package br.com.zupacademy.paises

import br.com.zupacademy.validators.EstadoUnicoValidator
import br.com.zupacademy.validators.ExistisId
import br.com.zutbpacademy.pais.Estado
import io.micronaut.core.annotation.Introspected
import javax.persistence.EntityManager
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
@EstadoUnicoValidator
class EstadoRequest(
    @field:NotBlank
    val nome: String,

    @field:NotNull
    @field:ExistisId(domainClass = Pais::class, fieldName = "id")
    val paisId: Long
) {
    fun toModel(manager: EntityManager): Estado {
        val pais = manager.find(Pais::class.java, paisId)
        return Estado(nome, pais)
    }
}


