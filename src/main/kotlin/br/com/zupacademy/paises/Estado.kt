package br.com.zutbpacademy.pais

import br.com.zupacademy.paises.Pais
import br.com.zupacademy.validators.UniqueValue
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(
    uniqueConstraints =
    [UniqueConstraint(columnNames = arrayOf("nome", "pais_id"))]
)
class Estado(
    val nome: String,

    @ManyToOne
    @JoinColumn(name = "pais_id")
    val pais: Pais
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    fun pertenceAoPais(pais: Pais): Boolean {
        if (pais.equals(this.pais)) return true
        return false
    }
}