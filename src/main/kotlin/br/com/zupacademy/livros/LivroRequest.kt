package br.com.zupacademy.livros

import br.com.zupacademy.autores.Autor
import br.com.zupacademy.categoria.Categoria
import br.com.zupacademy.validators.ExistisId
import br.com.zupacademy.validators.UniqueValue
import com.fasterxml.jackson.annotation.JsonFormat
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.EntityManager
import javax.validation.constraints.*

@Introspected
class LivroRequest(
    @field:NotBlank
    @field:UniqueValue(domainClass = Livro::class, fieldName = "titulo")
    val titulo: String,

    @field:NotNull @field:NotBlank @field:Size(max = 500)
    val resumo: String,

    val sumario: String?,

    @field:NotNull @field:Min(20)
    val preco: BigDecimal,

    @field:NotNull @field:Min(100)
    val paginas: Int,

    @field:NotBlank
    @field:UniqueValue(domainClass = Livro::class, fieldName = "isbn")
    val isbn: String,

    @field:Future @field:NotNull
    @field:JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    val dataPublicacao: LocalDate,

    @field:NotNull @field:Min(value = 1)
    @ExistisId(domainClass = Categoria::class, fieldName = "id")
    val categoriaId: Long,

    @field:NotNull @field:Min(value = 1)
    @ExistisId(domainClass = Autor::class, fieldName = "id")
    val autorId: Long
) {
    fun toModel(manager: EntityManager): Livro {

        val categoria = manager.find(Categoria::class.java, categoriaId)
        val autor = manager.find(Autor::class.java, autorId)

        return Livro(
            titulo, resumo, sumario, preco, paginas, isbn, dataPublicacao, categoria, autor
        )
    }
}