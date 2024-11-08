package com.consumer.content.provider.glue.humans.di

import com.consumer.content.humans.domain.repositories.HumanRepository
import com.consumer.content.provider.glue.humans.repositories.AdapterHumanRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HumanRepositoryModule {

    @Binds
    fun bindHumanRepository(repository: AdapterHumanRepository): HumanRepository

}