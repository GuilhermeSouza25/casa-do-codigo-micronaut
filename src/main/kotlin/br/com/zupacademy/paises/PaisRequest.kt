package br.com.zupacademy.paises

import br.com.zupacademy.validators.UniqueValue
import io.micronaut.core.annotation.Introspected
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Introspected
class PaisRequest(
    @field:NotBlank
    @field:UniqueValue(domainClass = Pais::class, fieldName = "nome")
    val nome: String
) {

    fun toModel(): Pais {
        return Pais(nome)
    }
}