package br.com.zupacademy.paises

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Pais(
    @field:NotBlank
    val nome: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun possuiEstado(manager: EntityManager): Boolean {

        return manager
            .createQuery("SELECT count(*) > 0 FROM Estado WHERE pais.id = $id")
            .singleResult as Boolean
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pais

        if (nome != other.nome) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nome.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }


}

