package br.com.zupacademy.livros

class LivroListaResponse(livro: Livro) {

    val id: Long = livro.id
    val titulo: String = livro.titulo
    val dataPublicacao: String = livro.formataData("dd/MM/yyyy")
    val sumario: String? = livro.sumario
}