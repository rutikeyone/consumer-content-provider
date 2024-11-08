package com.consumer.content.provider.glue.humans.repositories

import com.consumer.content.core.common.Container
import com.consumer.content.data.HumanDataRepository
import com.consumer.content.humans.domain.entities.Human
import com.consumer.content.humans.domain.repositories.HumanRepository
import com.consumer.content.provider.glue.humans.mappers.HumanMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterHumanRepository @Inject constructor(
    private val humanMapper: HumanMapper,
    private val humanDataRepository: HumanDataRepository,
) : HumanRepository {

    override fun observeAll(silently: Boolean): Flow<Container<List<Human>>> {
        return humanDataRepository.observeHumans(silently).map { data ->
            return@map data.map { item ->
                item.map { humanMapper.toHuman(it) }
            }
        }
    }

    override fun observeHuman(
        silently: Boolean,
        id: Long,
        requiredObserver: Boolean,
    ): Flow<Container<Human>> {
        return humanDataRepository.observeHuman(silently, id, requiredObserver).map { container ->
            container.map { model -> humanMapper.toHuman(model) }
        }
    }

}