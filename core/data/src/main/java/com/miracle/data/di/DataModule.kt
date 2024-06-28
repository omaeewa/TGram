package com.miracle.data.di

import com.miracle.data.repository.AuthRepository
import com.miracle.data.repository.AuthRepositoryTdLib
import com.miracle.data.repository.ChatsRepository
import com.miracle.data.repository.ChatsRepositoryTdLib
import com.miracle.data.repository.UserRepository
import com.miracle.data.repository.UserRepositoryTdLib
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
    fun provideTdLibRepository(impl: AuthRepositoryTdLib): AuthRepository

    @Singleton
    @Binds
    fun provideUserRepository(impl: UserRepositoryTdLib): UserRepository

    @Singleton
    @Binds
    fun provideChatsRepository(impl: ChatsRepositoryTdLib): ChatsRepository

    companion object {

        @Singleton
        @Provides
        fun provideTelegramApi() = TelegramFlow().apply {
            attachClient()
        }
    }
}