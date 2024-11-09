package com.consumer.content.humans.presentation.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.consumer.content.core.common.Container
import com.consumer.content.core.presentation.BaseViewModel
import com.consumer.content.core.presentation.Event
import com.consumer.content.humans.domain.entities.Human
import com.consumer.content.humans.domain.repositories.HumanRepository
import com.contentprovider.core.presentation.flow.RestartableStateFlow
import com.contentprovider.core.presentation.flow.restartableStateIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HumanListViewModel @Inject constructor(
    private val humanRepository: HumanRepository,
) : BaseViewModel() {

    val humanListFlow: RestartableStateFlow<Container<List<Human>>> =
        humanRepository.observeAll(true)
            .catch { emit(Container.Error(it)) }
            .restartableStateIn(
                viewModelScope,
                SharingStarted.Lazily,
                Container.Pending,
            )

    val isRefreshFlow = humanListFlow.map { it is Container.Pending }

    fun onEvent(uiEvent: HumanListUiEvent) {
        when (uiEvent) {
            HumanListUiEvent.Restart -> humanListFlow.restart()
        }
    }

}