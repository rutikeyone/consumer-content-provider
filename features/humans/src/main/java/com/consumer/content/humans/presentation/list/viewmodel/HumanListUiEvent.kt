package com.consumer.content.humans.presentation.list.viewmodel

sealed class HumanListUiEvent {
    data object Restart: HumanListUiEvent()
}