package br.com.zupacademy.autores

import br.com.zupacademy.shared.CepClient
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.transaction.SynchronousTransactionManager
import io.micronaut.transaction.annotation.TransactionalEventListener
import io.micronaut.validation.Validated
import java.sql.Connection
import java.util.*
import javax.persistence.EntityManager
import javax.validation.Valid


@Validated
@Controller("/autores")
class AutorController(
    private val repository: AutorRepository,
    private val manager: EntityManager,
    private val eventPublisher: ApplicationEventPublisher,
    private val transactionManager: SynchronousTransactionManager<Connection>,
    private val cepClient: CepClient
) {

    @Post
    //@Transactional //para testar o @TransactionalEventListener abaixo
    fun cadastra(@Body @Valid request: AutorRequest): HttpResponse<Any> {

        val enderecoResponse = cepClient.consulta(request.cep)

        val autor = request.toModel(enderecoResponse.body())
        transactionManager.executeWrite { manager.persist(autor) }

        val uri = UriBuilder.of("/autores/{id}").expand(mutableMapOf(Pair("id", autor.id)))
        eventPublisher.publishEvent(NovoAutorEvent(autor))

        return HttpResponse.created(uri)
    }
    class NovoAutorEvent(val autor: Autor)

    @TransactionalEventListener
    open fun onNovoAutor(event: NovoAutorEvent) {
        println("Autor = ${event.autor.nome}")
    }

    @Get()
    fun listar(@QueryValue email: Optional<String>): HttpResponse<Any> {

        when {
            email.isPresent -> {
                val possivelAutor = repository.findByEmail(email.get())
                if (possivelAutor.isEmpty) return HttpResponse.notFound()
                return HttpResponse.ok(AutorResponse(possivelAutor.get()))
            }
            else -> {
                val autores: List<Autor> = repository.findAll()
                val response = autores.map { autor -> AutorResponse(autor) }
                return HttpResponse.ok(response)
            }

        }


    }

    @Put("/{id}")
    fun atualizaAutor(
        @PathVariable(value = "id") id: Long,
        @Body @Valid request: AutorRequest): HttpResponse<Any> {

        val possivelAutor = repository.findById(id)

        if (possivelAutor.isEmpty) return HttpResponse.notFound()

        val autor = possivelAutor.get()
        autor.atualiza(request)
        repository.update(autor)

        return HttpResponse.ok(AutorResponse(autor))
    }

    @Delete("/{id}")
    fun deleta(@PathVariable(value = "id") id: Long): HttpResponse<Any> {

        val possivelAutor = repository.findById(id)

        if(possivelAutor.isEmpty) return HttpResponse.notFound()

        repository.deleteById(id)

        return HttpResponse.ok()
    }
}