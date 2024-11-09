package com.consumer.content.humans.presentation.details.viewmodel

import com.consumer.content.humans.domain.entities.Human

sealed class HumanDetailsUiState {

    open fun getOrNull(): Data? {
        return null
    }

    data object Pure : HumanDetailsUiState()

    data object Pending : HumanDetailsUiState()

    data object Empty : HumanDetailsUiState()

    data class Data(
        val human: Human,
        val name: String = "",
        val surname: String = "",
        val age: String = "",
    ) : HumanDetailsUiState() {

        override fun getOrNull(): Data {
            return this
        }

    }

    data class Error(val exception: Throwable) : HumanDetailsUiState()
}