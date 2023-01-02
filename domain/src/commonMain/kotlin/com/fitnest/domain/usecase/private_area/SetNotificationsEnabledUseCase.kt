package com.fitnest.domain.usecase.private_area

import com.fitnest.domain.repository.DataStoreRepository

class SetNotificationsEnabledUseCase(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(areEnabled: Boolean) =
        dataStoreRepository.saveNotificationsEnabled(areEnabled)
}
