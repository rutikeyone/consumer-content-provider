package com.consumer.content.provider.glue.humans.mappers

import com.consumer.content.data.models.HumanModel
import com.consumer.content.humans.domain.entities.Human
import javax.inject.Inject

class HumanMapper @Inject constructor() {

    fun toHumanModel(entity: Human): HumanModel {
        return HumanModel(
            id = entity.id,
            name = entity.name,
            surname = entity.surname,
            age = entity.age,
        )
    }

    fun toHuman(model: HumanModel): Human {
        return Human(
            id = model.id,
            name = model.name,
            surname = model.surname,
            age = model.age,
        )
    }

}