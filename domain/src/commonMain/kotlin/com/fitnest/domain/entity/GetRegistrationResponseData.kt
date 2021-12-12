package com.fitnest.domain.entity

data class GetRegistrationResponseData(
    val step: String? = null,
    val fields: RegistrationStepModel? = null,
    val validationSchema: Map<String, Any?>? = null
)

