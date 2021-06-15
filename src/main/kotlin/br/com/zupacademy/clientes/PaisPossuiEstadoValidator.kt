package br.com.zupacademy.validators

import br.com.zupacademy.clientes.EnderecoRequest
import br.com.zupacademy.paises.Pais
import br.com.zutbpacademy.pais.Estado
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import java.util.*
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Validator
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.reflect.KClass

private const val campo = "{fieldName}"

@Target(
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.TYPE,
    AnnotationTarget.FIELD,
    AnnotationTarget.CLASS,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER
)
@Retention(RUNTIME)
@Constraint(validatedBy = [PaisPossuiEstado::class])
annotation class PaisPossuiEstadoValidator(
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Any>> = [],
)

@Transactional
class PaisPossuiEstado : ConstraintValidator<PaisPossuiEstadoValidator, EnderecoRequest> {

    @Inject
    lateinit var manager: EntityManager

    override fun isValid(
        @Nullable value: EnderecoRequest,
        @NonNull context: ConstraintValidatorContext
    ): Boolean {

        val paisId = value.paisId
        val estadoId = value.estadoId

        if (paisId == null) return true

        val pais = manager.find(Pais::class.java, paisId)
        val paisPossuiEstado = pais.possuiEstado(manager)

        when {
            paisPossuiEstado && estadoId == null -> {
                context.message("deve ser informado um estado para este país", "paisId")
                return false
            }
            !paisPossuiEstado && estadoId != null -> {
                context.message("país não possui estados", "paisId")
                return false
            }
            paisPossuiEstado -> {
                val estado = manager.find(Estado::class.java, estadoId)
                val estadoPertenceAoPais = estado.pertenceAoPais(pais)

                if (!estadoPertenceAoPais) {
                    context.message("estado não pertence ao país infomado", "estadoId")
                    return false
                }
                return true
            }
            else -> return true
        }
    }

//        if (paisPossuiEstado && estadoId == null) {
//            context.message("deve ser informado um estado para este país", "paisId")
//            return false
//        } else if (paisPossuiEstado) {
//
//            val estado = manager.find(Estado::class.java, estadoId)
//
//            if (estado.pertenceAoPais(pais)) return true
//                context.message("estado não pertence a pais informado", "estadoId")
//            return false
//        } else {
//            return true
//        }
}


fun ConstraintValidatorContext.message(message: String, field: String) {
    this.disableDefaultConstraintViolation()
    this.buildConstraintViolationWithTemplate(message)
        .addPropertyNode(field)
        .addConstraintViolation()

}