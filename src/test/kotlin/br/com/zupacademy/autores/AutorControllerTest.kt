package br.com.zupacademy.autores

import br.com.zupacademy.shared.CepClient
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.transaction.SynchronousTransactionManager
import org.junit.jupiter.api.*
import org.mockito.Mockito
import java.sql.Connection
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional

@MicronautTest(
    rollback = true, //default = true
    transactionMode = TransactionMode.SEPARATE_TRANSACTIONS, //default = SEPARATE_TRANSACTIONS
    transactional = false //default = true
)
internal class AutorControllerTest {

    @field:Inject
    lateinit var repository: AutorRepository

    @field:Inject
    lateinit var manager: EntityManager

    @field:Inject
    lateinit var transactionManager: SynchronousTransactionManager<Connection>

    @field:Inject
    lateinit var cepClient: CepClient

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    lateinit var enderecoClientResponse: EnderecoClientResponse
    lateinit var autorRequest: AutorRequest
    lateinit var autor: Autor

    @BeforeEach
    internal fun setup() {

        enderecoClientResponse = EnderecoClientResponse("25620040", "Rua Dezesseis de Março", "", "Centro", "Petrópolis", "RJ", "3303906", "", "24", "5877")
        autorRequest = AutorRequest("José Guilherme", "teste@teste.com", "teste", "25620040")
        autor = autorRequest.toModel(enderecoClientResponse)
    }

    @AfterEach
    internal fun tearDown() {
        repository.deleteAll()
    }

    @Test
    internal fun `deve retornar um autor quando um email válido for informado`() {

        repository.save(autor)

        val response = httpClient.toBlocking()
            .exchange("/autores?email=${autor.email}", AutorDetalheResponse::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertNotNull(response.body())
        Assertions.assertEquals(autor.nome, response.body()!!.nome)
        Assertions.assertEquals(autor.email, response.body()!!.email)
        Assertions.assertEquals(autor.descricao, response.body()!!.descricao)
    }

    @Test
    internal fun `deve cadastrar um novo autor`() {

        val autorRequest = AutorRequest("teste2", "teste2@teste2.com", "teste2", "25620040")
        Mockito.`when`(cepClient.consulta(autorRequest.cep)).thenReturn(HttpResponse.ok(enderecoClientResponse))
        val request = HttpRequest.POST("/autores", autorRequest)
        val response = httpClient.toBlocking().exchange(request, Any::class.java)

        Assertions.assertEquals(HttpStatus.CREATED, response.status)
        Assertions.assertTrue(response.headers.contains("Location"))
        Assertions.assertTrue(response.header("location")!!.contains("/autores/\\d".toRegex()))
    }

    @MockBean(CepClient::class)
    fun cepClientMock(): CepClient {
        return Mockito.mock(CepClient::class.java)
    }
}
