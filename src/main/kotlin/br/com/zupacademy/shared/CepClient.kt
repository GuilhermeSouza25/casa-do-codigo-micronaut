package br.com.zupacademy.shared

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client
interface CepClient {

    @Get(value = "https://viacep.com.br/ws/{cep}/json/")
    fun consulta(cep: String): HttpResponse<EnderecoResponse?>
}