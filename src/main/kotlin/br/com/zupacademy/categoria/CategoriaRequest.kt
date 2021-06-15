package br.com.zupacademy.categoria

import br.com.zupacademy.validators.UniqueValue
import javax.validation.constraints.NotBlank

class CategoriaRequest(
    @field:NotBlank
    @field:UniqueValue(domainClass = Categoria::class, fieldName = "nome")
    val nome: String
) {

    fun toModel(): Categoria {
        return Categoria(nome)
    }
}