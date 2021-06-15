package br.com.zupacademy.autores

import br.com.zupacademy.shared.EnderecoResponse
import javax.persistence.Embeddable

@Embeddable
class EnderecoAutor(
    enderecoResponse: EnderecoResponse?
) {
    val cep: String? = enderecoResponse?.cep
    val logradouro: String? = enderecoResponse?.logradouro
    val complemento: String? = enderecoResponse?.complemento
    val bairro: String? = enderecoResponse?.bairro
    val localidade: String? = enderecoResponse?.localidade
    val uf: String? = enderecoResponse?.uf


}
