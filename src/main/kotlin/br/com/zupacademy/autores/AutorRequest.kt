package br.com.zupacademy.autores

import br.com.zupacademy.validators.UniqueValue
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Introspected
data class  AutorRequest(

    @field:NotBlank
    val nome: String,

    @field:NotBlank @field:Email
    @field:UniqueValue(domainClass = Autor::class, fieldName = "email")
    val email: String,

    @field:NotBlank @field:Size(max = 400)
    val descricao: String,

    @field:NotBlank @field:Pattern(regexp = "[0-9]{8}")
    val cep: String,
) {

    fun toModel(enderecoResponse: EnderecoClientResponse?): Autor {

        val endereco = enderecoResponse?.toModel()

        return Autor(nome, email, descricao, endereco)
    }

}
