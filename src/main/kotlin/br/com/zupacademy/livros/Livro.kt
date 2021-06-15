package br.com.zupacademy.livros

import br.com.zupacademy.autores.Autor
import br.com.zupacademy.categoria.Categoria
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
class Livro(
    val titulo: String,
    val resumo : String,
    @Column(columnDefinition = "TEXT")
    val sumario : String?,
    val preco: BigDecimal,
    val paginas: Int,
    val isbn: String,
    val dataPublicacao: LocalDate,
    @ManyToOne
    val categoria: Categoria,
    @ManyToOne
    val autor: Autor
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    fun formataData(pattern: String): String {
        return dataPublicacao.format(DateTimeFormatter.ofPattern(pattern))
    }
}
