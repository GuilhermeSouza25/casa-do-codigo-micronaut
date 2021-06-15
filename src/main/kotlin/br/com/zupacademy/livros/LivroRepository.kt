package br.com.zupacademy.livros

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import io.micronaut.data.model.Page
import java.util.*

@Repository
interface LivroRepository: JpaRepository<Livro, Long> {

    @Query("SELECT l FROM Livro l WHERE titulo LIKE :titulo",
        countQuery = "SELECT count(*) FROM Livro l WHERE titulo LIKE :titulo")
    fun buscaPorTitulo(titulo: String, paginacao: Paginacao): Page<Livro>


}