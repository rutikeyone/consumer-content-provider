package com.consumer.content.data.di

import android.content.Context
import com.consumer.content.data.HumanDataRepository
import com.consumer.content.data.repositories.HumanDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
annotation class IODispatcher

@Module
@InstallIn(SingletonComponent::class)
interface HumanDataModule {

    @Binds
    fun bindHumanDataRepository(humanDataRepository: HumanDataRepositoryImpl): HumanDataRepository

    companion object {

        @Provides
        @IODispatcher
        fun provideIODispatcher(): CoroutineDispatcher {
            return Dispatchers.IO
        }

    }

}