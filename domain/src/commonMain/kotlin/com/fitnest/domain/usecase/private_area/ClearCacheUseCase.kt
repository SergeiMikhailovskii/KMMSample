package com.fitnest.domain.usecase.private_area

import com.fitnest.domain.repository.DatabaseRepository

class ClearCacheUseCase internal constructor(
    private val databaseRepository: DatabaseRepository
) {

    operator fun invoke() = runCatching {
        databaseRepository.deleteDashboard()
        databaseRepository.deleteActivityTrackerResponse()
    }

}