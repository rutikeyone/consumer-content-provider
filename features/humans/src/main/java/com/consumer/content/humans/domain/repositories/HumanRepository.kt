package com.consumer.content.humans.domain.repositories

import com.consumer.content.core.common.Container
import com.consumer.content.humans.domain.entities.Human
import kotlinx.coroutines.flow.Flow

interface HumanRepository {

    fun observeAll(silently: Boolean = false): Flow<Container<List<Human>>>

    fun observeHuman(
        silently: Boolean,
        id: Long,
        requiredObserver: Boolean = true,
    ): Flow<Container<Human>>

}