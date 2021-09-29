package br.com.zupacademy.autores


class AutorListaResponse(autor: Autor) {

    val nome = autor.nome
    val email = autor.email
    val descricao = autor.descricao
    val criadoEm = autor.formataData("dd/MM/yyyy")
}
