package br.com.zupacademy.livros

import java.math.BigDecimal

class LivroDetalheResponse(livro: Livro) {
    var titulo: String = livro.titulo
    val resumo: String = livro.resumo
    val sumario: String? = livro.sumario
    val preco: BigDecimal = livro.preco
    val paginas: Int = livro.paginas
    val isbn: String = livro.isbn
    val autor: DetalheAutorResponse = DetalheAutorResponse(livro.autor)
    val dataPublicao: String = livro.formataData("dd/MM/yyyy")
}