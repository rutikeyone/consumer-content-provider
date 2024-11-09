package com.consumer.content.humans.presentation.list

import com.consumer.content.core.common.Container
import com.consumer.content.core.presentation.BaseViewModel
import com.consumer.content.humans.domain.entities.Human
import com.consumer.content.humans.domain.repositories.HumanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class HumanListViewModel @Inject constructor(
    private val humanRepository: HumanRepository,
) : BaseViewModel() {

    private val _humanListState: MutableStateFlow<Container<List<Human>>> =
        MutableStateFlow(Container.Pending)
    val humanListState = _humanListState.asStateFlow()

    init {

        viewModelScope.launch {
            humanRepository.observeAll(true)
                .onEach { _humanListState.tryEmit(it) }
                .catch { _humanListState.tryEmit(Container.Error(it)) }
                .collect()
        }
    }

    override fun handler(context: CoroutineContext, exception: Throwable) {
        _humanListState.tryEmit(Container.Error(exception))
    }

}