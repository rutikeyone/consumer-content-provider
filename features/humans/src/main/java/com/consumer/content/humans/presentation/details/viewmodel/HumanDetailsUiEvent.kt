package com.consumer.content.humans.presentation.details.viewmodel

sealed class HumanDetailsUiEvent {

    data class DetailsName(val value: String): HumanDetailsUiEvent()

    data class DetailsSurname(val value: String): HumanDetailsUiEvent()

    data class DetailsAge(val value: String): HumanDetailsUiEvent()

    data object Restart: HumanDetailsUiEvent()

    data object ObserveDetails: HumanDetailsUiEvent()

    data object Details: HumanDetailsUiEvent()

    data object Delete: HumanDetailsUiEvent()
}