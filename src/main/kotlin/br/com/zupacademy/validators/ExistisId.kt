package br.com.zupacademy.validators

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
private const val classe = "\${domainClass.simpleName}"

@Retention(RUNTIME)
@Constraint(validatedBy = [ExistisIdValidator::class])
annotation class ExistisId(
    val message: String = "$classe inexistente",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Any>> = [],
    val domainClass: KClass<*>,
    val fieldName: String
)

@Transactional
class ExistisIdValidator : ConstraintValidator<ExistisId, Long> {

    @Inject
    lateinit var manager: EntityManager

    lateinit var field: String
    lateinit var clazz: KClass<*>

    override fun initialize(constraintAnnotation: ExistisId) {
        field = constraintAnnotation.fieldName.toLowerCase()
        clazz = constraintAnnotation.domainClass
    }

    override fun isValid(
        @Nullable value: Long?,
        @NonNull annotationMetadata: AnnotationValue<ExistisId>,
        @NonNull context: ConstraintValidatorContext
    ): Boolean {

        if (value == null) return true

        val resultList = manager
            .createQuery("SELECT 1 FROM  ${clazz.simpleName} WHERE $field = :value")
            .setParameter("value", value)
            .resultList

        return resultList.isNotEmpty()
    }
}