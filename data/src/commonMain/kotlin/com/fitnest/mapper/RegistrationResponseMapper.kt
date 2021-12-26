package com.fitnest.mapper

import com.fitnest.domain.entity.GetRegistrationResponseData
import com.fitnest.domain.entity.RegistrationStepModel
import com.fitnest.domain.entity.validator.MinLengthValidator
import com.fitnest.domain.entity.validator.RegExpValidator
import com.fitnest.domain.entity.validator.RequiredValidator
import com.fitnest.domain.entity.validator.Validator
import kotlinx.serialization.json.*

class RegistrationResponseMapper(
    private val json: Json
) : ResponseMapper<JsonObject, GetRegistrationResponseData> {

    override fun map(source: JsonObject?): GetRegistrationResponseData {
        val step = source?.get("step")?.jsonPrimitive?.content
        val fields = source?.get("fields")?.jsonObject
        val validationSchema = mapValidationSchema(source?.get("validation_schema")?.jsonObject)
        val stepData = mapStepData(fields, step)
        return GetRegistrationResponseData(
            step = step,
            fields = stepData,
            validationSchema = validationSchema
        )
    }

    private fun mapStepData(fields: JsonObject?, step: String?): RegistrationStepModel? {
        if (fields == null) return null
        return when (step) {
            "STEP_CREATE_ACCOUNT" -> {
                return json.decodeFromJsonElement<RegistrationStepModel.CreateAccountStepModel>(fields)
            }
            else -> null
        }
    }

    private fun mapValidationSchema(map: JsonObject?): Map<String, List<Validator>> {
        val mappedValidationSchema = mutableMapOf<String, List<Validator>>()
        val entries = map?.entries
        entries?.forEach {
            val validators = it.value
            val mappedValidators = mutableListOf<Validator>()
            if (validators is JsonArray) {
                validators.forEach {
                    mappedValidators.add(mapValidator(it as JsonObject))
                }
            }
            mappedValidationSchema[it.key] = mappedValidators
        }
        return mappedValidationSchema
    }

    private fun mapValidator(validator: JsonObject): Validator {
        return when (validator["type"]?.jsonPrimitive?.content) {
            "required" -> {
                json.decodeFromJsonElement<RequiredValidator>(validator)
            }
            "regExp" -> {
                json.decodeFromJsonElement<RegExpValidator>(validator)
            }
            "minLength" -> {
                json.decodeFromJsonElement<MinLengthValidator>(validator)
            }
            else -> {
                throw RuntimeException("unknown validator")
            }
        }
    }
}