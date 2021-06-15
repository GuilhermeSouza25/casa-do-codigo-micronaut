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
@Constraint(validatedBy = [UniqueValueValidator::class])
annotation class UniqueValue(
    val message: String = "$campo já existente",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Any>> = [],
    val domainClass: KClass<*>,
    val fieldName: String
)

@Transactional
class UniqueValueValidator : ConstraintValidator<UniqueValue, String> {

    @Inject
    lateinit var manager: EntityManager

    lateinit var field: String
    lateinit var clazz: KClass<*>

    override fun initialize(constraintAnnotation: UniqueValue) {
        field = constraintAnnotation.fieldName.toLowerCase()
        clazz = constraintAnnotation.domainClass
    }

    override fun isValid(
        @Nullable value: String?,
        @NonNull annotationMetadata: AnnotationValue<UniqueValue>,
        @NonNull context: ConstraintValidatorContext
    ): Boolean {

        val resultList = manager
            .createQuery("SELECT 1 FROM  ${clazz.simpleName} WHERE $field = :value")
            .setParameter("value", value?.toLowerCase())
            .resultList
        return resultList.isEmpty()
    }
}