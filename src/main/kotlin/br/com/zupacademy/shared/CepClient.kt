package br.com.zupacademy.shared

import br.com.zupacademy.autores.EnderecoClientResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client
interface CepClient {

    @Get(value = "https://viacep.com.br/ws/{cep}/json/", produces = [MediaType.APPLICATION_JSON])
    fun consulta(cep: String): HttpResponse<EnderecoClientResponse?>
}