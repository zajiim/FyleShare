package com.fyndr.fileshare.core.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.fyndr.fileshare.data.datamanager.LocalUserManagerImpl
import com.fyndr.fileshare.data.datamanager.fyleShareDataStore
import com.fyndr.fileshare.domain.datamanager.LocalUserManager
import com.fyndr.fileshare.domain.use_cases.onboarding.OnBoardingUseCases
import com.fyndr.fileshare.domain.use_cases.onboarding.ReadOnboardingUseCase
import com.fyndr.fileshare.domain.use_cases.onboarding.SaveOnboardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(
        application: Application
    ): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideFyleShareDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.fyleShareDataStore
    }

    @Provides
    @Singleton
    fun provideLocalUserManager(
        @ApplicationContext context: Context
    ): LocalUserManager = LocalUserManagerImpl(context)

    @Provides
    fun provideReadOnBoardingUseCase(
        localUserManager: LocalUserManager
    ): ReadOnboardingUseCase = ReadOnboardingUseCase(localUserManager)

    @Provides
    fun provideSaveOnBoardingUseCase(
        localUserManager: LocalUserManager
    ): SaveOnboardingUseCase = SaveOnboardingUseCase(localUserManager)


    @Provides
    fun provideOnBoardingUseCases(
        readOnboardingUseCase: ReadOnboardingUseCase,
        saveOnboardingUseCase: SaveOnboardingUseCase
    ): OnBoardingUseCases = OnBoardingUseCases(
        readOnboardingUseCase = readOnboardingUseCase,
        saveOnboardingUseCase = saveOnboardingUseCase
    )

}