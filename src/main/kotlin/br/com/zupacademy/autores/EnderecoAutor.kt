package br.com.zupacademy.autores

import javax.persistence.Embeddable

@Embeddable
data class EnderecoAutor(
    val cep: String?,
    val logradouro: String?,
    val complemento: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?
)


