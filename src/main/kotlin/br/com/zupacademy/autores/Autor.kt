package br.com.zupacademy.autores

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
class Autor(
    nome: String,
    email: String,
    descricao: String,
    @field:Embedded val endereco: EnderecoAutor?
) {

    var nome: String = nome
        private set
    var email: String = email
        private set
    var descricao: String = descricao
        private set

    val criadoEm: LocalDate = LocalDate.now()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    fun atualiza(autorRequest: AutorRequest) {
        this.nome = autorRequest.nome
    }

    fun formataData(pattern: String): String {
        return criadoEm.format(DateTimeFormatter.ofPattern(pattern))
    }
}
