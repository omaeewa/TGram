package com.miracle.data.di

import com.miracle.data.repository.AuthRepository
import com.miracle.data.repository.TdLibAuthAuthRepository
import com.miracle.data.repository.TdLibUserRepository
import com.miracle.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.telegram.core.TelegramFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun provideTdLibRepository(impl: TdLibAuthAuthRepository): AuthRepository

    @Singleton
    @Binds
    fun provideUserRepository(impl: TdLibUserRepository): UserRepository

    companion object {

        @Singleton
        @Provides
        fun provideTelegramApi() = TelegramFlow().apply {
            attachClient()
        }
    }
}