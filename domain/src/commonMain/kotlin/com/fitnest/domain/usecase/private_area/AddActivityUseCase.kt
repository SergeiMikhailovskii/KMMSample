package com.fitnest.domain.usecase.private_area

import com.fitnest.domain.entity.request.AddActivityRequest
import com.fitnest.domain.entity.request.DeleteActivityRequest
import com.fitnest.domain.entity.response.ActivityTrackerPageResponse
import com.fitnest.domain.repository.NetworkRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class AddActivityUseCase(
    private val repository: NetworkRepository,
    private val json: Json
) {

    suspend operator fun invoke(request: AddActivityRequest) =
        runCatching {
            repository.addActivity(request)
            repository.getActivityTrackerPage()
        }.map {
            val decoded = json.decodeFromJsonElement<ActivityTrackerPageResponse>(it.data)
            decoded.widgets
        }
}
