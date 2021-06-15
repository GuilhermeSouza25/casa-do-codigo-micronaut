package br.com.zupacademy.validators

import org.hibernate.validator.constraints.CompositionType
import org.hibernate.validator.constraints.ConstraintComposition
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.Constraint
import javax.validation.ReportAsSingleViolation
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.reflect.KClass

private const val campo = "{fieldName}"

@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR)
@ReportAsSingleViolation
@Retention(RUNTIME)
@Constraint(validatedBy = [])
annotation class Documento(
    val message: String = "documento em formato inválido",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Any>> = [],
)

