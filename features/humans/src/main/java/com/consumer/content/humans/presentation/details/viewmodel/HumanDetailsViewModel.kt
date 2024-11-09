package com.consumer.content.humans.presentation.details.viewmodel

import com.consumer.content.core.common.Container
import com.consumer.content.core.presentation.BaseViewModel
import com.consumer.content.humans.domain.entities.Human
import com.consumer.content.humans.domain.repositories.HumanRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val silently = false
private const val requiredObserver = false

class HumanDetailsViewModel @AssistedInject constructor(
    @Assisted private val id: Long,
    private val humanRepository: HumanRepository,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<HumanDetailsUiState> =
        MutableStateFlow(HumanDetailsUiState.Pending)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            humanRepository
                .observeHuman(silently, id, requiredObserver)
                .catch { _uiState.tryEmit(HumanDetailsUiState.Error(it)) }
                .onEach {
                    val state = mapContainerToUiState(it)
                    _uiState.tryEmit(state)
                }
                .collect()
        }
    }

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

    @AssistedFactory
    interface Factory {
        fun create(id: Long): HumanDetailsViewModel
    }

    interface FactoryProvider {
        fun provideViewModelFactory(): Factory
    }

}