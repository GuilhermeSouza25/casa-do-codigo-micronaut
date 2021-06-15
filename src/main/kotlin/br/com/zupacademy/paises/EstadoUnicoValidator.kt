package br.com.zupacademy.validators

import br.com.zupacademy.paises.EstadoRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.Constraint
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.reflect.KClass

private const val campo = "{fieldName}"

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(RUNTIME)
@Constraint(validatedBy = [EstadoUnico::class])
annotation class EstadoUnicoValidator(
    val message: String = "estado já pertence ao país",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Any>> = [],
)

@Transactional
class EstadoUnico : ConstraintValidator<EstadoUnicoValidator, EstadoRequest> {

    @Inject
    lateinit var manager: EntityManager

    override fun isValid(
        @Nullable value: EstadoRequest,
        @NonNull annotationMetadata: AnnotationValue<EstadoUnicoValidator>,
        @NonNull context: ConstraintValidatorContext
    ): Boolean {

        val nome: String = value.nome
        val paisId: Long = value.paisId

        return manager
            .createQuery("SELECT count(*) < 1 FROM Estado WHERE nome = '$nome' AND pais.id = $paisId")
            .singleResult as Boolean
    }
}