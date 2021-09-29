package br.com.zupacademy.livros

import br.com.zupacademy.autores.Autor

class DetalheAutorResponse(autor: Autor) {
    val nome: String? = autor.nome
    val descricao: String = autor.descricao
}
