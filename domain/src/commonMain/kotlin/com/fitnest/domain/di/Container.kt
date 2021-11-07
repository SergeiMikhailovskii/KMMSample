package com.fitnest.domain.di

import com.fitnest.domain.usecase.GenerateTokenUseCase
import com.fitnest.domain.usecase.LoginUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val useCaseModule = DI.Module("use case module") {
    bind<LoginUseCase>() with singleton {
        LoginUseCase(di)
    }
    bind<GenerateTokenUseCase>() with singleton {
        GenerateTokenUseCase(di)
    }
}