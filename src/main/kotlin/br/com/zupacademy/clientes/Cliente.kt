package br.com.zupacademy.clientes

import br.com.zupacademy.paises.Pais
import br.com.zutbpacademy.pais.Estado
import javax.persistence.*

@Entity
class Cliente(
    val email: String,
    val nome: String,
    val sobrenome: String,
    val documento: String,
    val telefone: String,

    @field:OneToOne(cascade = [CascadeType.PERSIST])
    val endereco: Endereco
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}