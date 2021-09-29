package br.com.zupacademy.livros;

import io.micronaut.data.model.Pageable
import io.micronaut.data.model.Sort
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.exceptions.HttpException
import io.micronaut.validation.Validated;
import java.util.*
import java.util.function.Consumer
import javax.persistence.EntityManager
import javax.transaction.Transactional

import javax.validation.Valid;

@Validated
@Controller("/livros")
class LivroController(
    val manager: EntityManager,
    val repository: LivroRepository
) {

    @Post
    @Transactional
    fun cadastra(@Body @Valid request: LivroRequest): HttpResponse<Any> {

        val livro = request.toModel(manager)

        repository.save(livro)

        return HttpResponse.status(HttpStatus.CREATED)
    }

    @Get("{?paginacao*}")
    fun listar(
        paginacao: Paginacao,
        @QueryValue titulo: Optional<String>
    ): HttpResponse<Any> {

        val livros = if (titulo.isEmpty) repository.findAll(paginacao)
        else repository.buscaPorTitulo("%${titulo.get()}%", paginacao)

//        titulo.ifPresentOrElse({repository.findAll()},
//            {repository.findAll(paginacao)})

        return HttpResponse.ok(livros.map { livro -> LivroListaResponse(livro) })
    }

    @Get("/{id}")
    fun detalhar(@PathVariable(value = "id") id: Long): HttpResponse<LivroDetalheResponse>? {
        return repository
            .findById(id)
            .map { livro -> LivroDetalheResponse(livro) }
            .map { livroResponse -> HttpResponse.ok(livroResponse) }
            .orElseGet { HttpResponse.notFound() }
    }
}

class Paginacao(
    @field:QueryValue() val pagina: Int,
    @field:QueryValue val tamanho: Int,
) : Pageable {

    var sort = "dataPublicacao"

    override fun getNumber(): Int {
        return pagina
    }

    override fun getSize(): Int {
        return tamanho
    }

    override fun getSort(): Sort {
        return Sort.of(listOf(Sort.Order.desc(sort)))
    }

}