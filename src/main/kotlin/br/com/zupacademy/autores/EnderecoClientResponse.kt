package br.com.zupacademy.autores

import io.micronaut.core.annotation.Introspected
import kotlin.math.log

@Introspected
data class EnderecoClientResponse(
    val cep: String?,
    val logradouro: String?,
    val complemento: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?,
    val ibge: String?,
    val gia: String?,
    val ddd: String?,
    val siafi: String?

) {
    fun toModel(): EnderecoAutor {
        return EnderecoAutor(cep, logradouro, complemento, bairro, localidade, uf)
    }
}




