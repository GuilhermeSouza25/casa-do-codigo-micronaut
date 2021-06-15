package br.com.zupacademy.clientes

import br.com.zupacademy.paises.Pais
import br.com.zutbpacademy.pais.Estado
import javax.persistence.*

@Entity
class Endereco(
    val logradouro: String,
    val complemento: String,
    val cidade: String,
    val cep: String,

    @OneToOne
    val pais: Pais,

    @OneToOne
    val estado: Estado,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}