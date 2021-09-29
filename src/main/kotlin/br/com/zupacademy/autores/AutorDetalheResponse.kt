package br.com.zupacademy.autores

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer

class AutorDetalheResponse(autor: Autor) {
    val nome = autor.nome
    val email = autor.email
    val descricao = autor.descricao

    @JsonDeserialize(using = LocalDateDeserializer::class)
    open val criadoEm = autor.formataData("yyyy-MM-dd")

    val endereco = object {
        val localidade = autor.endereco?.localidade
        val bairro = autor.endereco?.bairro
        val uf = autor.endereco?.uf
    }



}


