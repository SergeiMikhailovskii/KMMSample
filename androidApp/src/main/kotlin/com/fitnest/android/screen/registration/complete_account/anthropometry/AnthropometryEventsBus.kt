package com.fitnest.android.screen.registration.complete_account.anthropometry

import kotlinx.coroutines.flow.MutableSharedFlow

interface AnthropometryEventsBusPublisher {
    suspend fun sendEvent(event: AnthropometryEvent)
}

interface AnthropometryEventsBusSubscriber {
    suspend fun subscribe(onEvent: (AnthropometryEvent) -> Unit)
}

interface AnthropometryEventsBus : AnthropometryEventsBusPublisher, AnthropometryEventsBusSubscriber

class AnthropometryEventsBusImpl(
    private val eventsFlow: MutableSharedFlow<AnthropometryEvent> = MutableSharedFlow()
) : AnthropometryEventsBus {
    override suspend fun sendEvent(event: AnthropometryEvent) {
        eventsFlow.emit(event)
    }

    override suspend fun subscribe(onEvent: (AnthropometryEvent) -> Unit) {
        eventsFlow.collect {
            onEvent(it)
        }
    }
}