package br.com.zupacademy.shared

import br.com.zupacademy.clientes.Endereco
import io.micronaut.core.annotation.Introspected

@Introspected
data class EnderecoResponse(
    val cep: String?,
    val logradouro: String?,
    val complemento: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?,
) {
}
