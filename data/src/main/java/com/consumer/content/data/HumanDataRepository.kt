package com.consumer.content.data

import com.consumer.content.core.common.Container
import com.consumer.content.data.models.HumanModel
import kotlinx.coroutines.flow.Flow

interface HumanDataRepository {
    suspend fun getHumans(): List<HumanModel>

    suspend fun getHuman(id: Long): HumanModel

    fun observeHumans(silently: Boolean = false): Flow<Container<List<HumanModel>>>

    fun observeHuman(
        silently: Boolean, id: Long,
        requiredObserver: Boolean = true,
    ): Flow<Container<HumanModel>>
}