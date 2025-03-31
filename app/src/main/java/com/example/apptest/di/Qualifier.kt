package com.example.apptest.di

import javax.inject.Qualifier

// ------------------------------------ COROUTINE CONFIG -------------------------------------------

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CPUDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher

