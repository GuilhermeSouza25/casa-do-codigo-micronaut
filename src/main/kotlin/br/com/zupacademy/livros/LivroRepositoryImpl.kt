package br.com.zupacademy.livros

import io.micronaut.context.annotation.Bean
import io.micronaut.data.model.Page
import java.util.*
import javax.inject.Singleton

@Singleton
class LivroRepositoryImpl(
    private val livroJPA: LivroJPA
): LivroRepository {

    override fun save(livro: Livro) {
        livroJPA.save(livro)
    }

    override fun buscaPorTitulo(titulo: String, paginacao: Paginacao): Page<Livro> {
        return livroJPA.buscaPorTitulo(titulo, paginacao)
    }

    override fun findAll(paginacao: Paginacao): Page<Livro> {
        return livroJPA.findAll(paginacao)
    }

    override fun findAll(): MutableList<Livro> {
        return livroJPA.findAll()
    }

    override fun findById(id: Long): Optional<Livro> {
        return livroJPA.findById(id)
    }
}