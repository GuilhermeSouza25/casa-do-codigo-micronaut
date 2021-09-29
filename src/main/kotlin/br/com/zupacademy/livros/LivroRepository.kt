package br.com.zupacademy.livros

import io.micronaut.data.model.Page
import java.util.*

interface LivroRepository {

    fun save(livro: Livro)
    fun buscaPorTitulo(titulo: String, paginacao: Paginacao): Page<Livro>
    fun findAll(paginacao: Paginacao): Page<Livro>
    fun findAll(): MutableList<Livro>
    fun findById(id: Long): Optional<Livro>
}