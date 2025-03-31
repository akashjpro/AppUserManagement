package com.example.apptest.di.module

import UsersRepositoryImpl
import com.example.apptest.data.remote.UsersApi
import com.example.apptest.domain.repository.UsersRepository
import com.example.apptest.domain.usecases.UsersUseCases
import com.example.apptest.domain.usecases.UsersUseCasesImpl
import com.example.apptest.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInstance(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersRepository(
        usersApi: UsersApi
    ): UsersRepository {
        return UsersRepositoryImpl(usersApi)
    }

    @Provides
    @Singleton
    fun provideUsersUseCases(usersRepository: UsersRepository): UsersUseCases {
        return UsersUseCasesImpl(usersRepository)
    }
}