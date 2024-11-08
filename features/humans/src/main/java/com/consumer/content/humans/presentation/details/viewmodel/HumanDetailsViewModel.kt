package com.consumer.content.humans.presentation.details.viewmodel

import com.consumer.content.core.common.Container
import com.consumer.content.core.presentation.BaseViewModel
import com.consumer.content.humans.domain.entities.Human
import com.consumer.content.humans.domain.repositories.HumanRepository
import com.contentprovider.core.presentation.flow.restartableStateIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map

private const val silently = false
private const val requiredObserver = false

class HumanDetailsViewModel @AssistedInject constructor(
    @Assisted private val id: Long,
    private val humanRepository: HumanRepository,
) : BaseViewModel() {

    private val humanDetailsFlow =
        humanRepository.observeHuman(silently, id, requiredObserver).restartableStateIn(
            viewModelScope,
            SharingStarted.Lazily,
            Container.Pending,
        )

    val uiState = humanDetailsFlow.map {
        return@map mapContainerToUiState(it)
    }

    val refreshState = humanDetailsFlow.map { it is Container.Pending }

    private fun mapContainerToUiState(container: Container<Human>): HumanDetailsUiState {
        return when (container) {
            Container.Pure -> HumanDetailsUiState.Pure
            Container.Empty -> HumanDetailsUiState.Empty
            Container.Pending -> HumanDetailsUiState.Pending
            is Container.Data -> HumanDetailsUiState.Data(
                human = container.data,
                name = container.data.name,
                surname = container.data.surname,
                age = container.data.age.toString(),
            )

            is Container.Error -> HumanDetailsUiState.Error(container.error)
        }
    }

    private fun restart() {
        humanDetailsFlow.restart()
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long): HumanDetailsViewModel
    }

    interface FactoryProvider {
        fun provideViewModelFactory(): Factory
    }

}