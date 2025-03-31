package com.example.apptest.di.module

import com.example.apptest.di.CPUDispatcher
import com.example.apptest.di.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ConfigModule {
    // ------------------------------------ COROUTINE CONFIG ---------------------------------------

    @CPUDispatcher
    @Provides
    fun provideCPUDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IODispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideAppCoroutineScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineName("AppCoroutine"))
}