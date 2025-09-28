package com.fyndr.fileshare.core.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.fyndr.fileshare.data.datamanager.AppDatabase
import com.fyndr.fileshare.data.datamanager.LocalUserManagerImpl
import com.fyndr.fileshare.data.datamanager.fyleShareDataStore
import com.fyndr.fileshare.data.naming.local.dao.UserNameDAO
import com.fyndr.fileshare.data.naming.repository.UserRepositoryImpl
import com.fyndr.fileshare.domain.datamanager.LocalUserManager
import com.fyndr.fileshare.domain.naming.repository.UserRepository
import com.fyndr.fileshare.domain.naming.use_case.GetUserNameUseCase
import com.fyndr.fileshare.domain.naming.use_case.NamingUseCases
import com.fyndr.fileshare.domain.naming.use_case.ReadNameStateUseCase
import com.fyndr.fileshare.domain.naming.use_case.SaveNameStateUseCase
import com.fyndr.fileshare.domain.naming.use_case.SaveUserNameUseCase
import com.fyndr.fileshare.domain.on_boarding.use_cases.onboarding.OnBoardingUseCases
import com.fyndr.fileshare.domain.on_boarding.use_cases.onboarding.ReadOnboardingUseCase
import com.fyndr.fileshare.domain.on_boarding.use_cases.onboarding.SaveOnboardingUseCase
import com.fyndr.fileshare.domain.send_or_receive.use_cases.GetUserDetailsUseCase
import com.fyndr.fileshare.utils.Constants
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
    fun providesReadNameStateUseCase(localUserManager: LocalUserManager): ReadNameStateUseCase {
        return ReadNameStateUseCase(localUserManager)
    }

    @Provides
    fun providesSaveNameStateUseCase(localUserManager: LocalUserManager): SaveNameStateUseCase {
        return SaveNameStateUseCase(localUserManager)
    }

    @Provides
    fun provideOnBoardingUseCases(
        readOnboardingUseCase: ReadOnboardingUseCase,
        saveOnboardingUseCase: SaveOnboardingUseCase,
        readNameStateUseCase: ReadNameStateUseCase,
        saveNameStateUseCase: SaveNameStateUseCase,
    ): OnBoardingUseCases = OnBoardingUseCases(
        readOnboardingUseCase = readOnboardingUseCase,
        saveOnboardingUseCase = saveOnboardingUseCase,
        readNameStateUseCase = readNameStateUseCase,
        saveNameStateUseCase = saveNameStateUseCase,
    )


    //Provides Database
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, Constants.FYLESHARE_DATABASE_NAME).build()
    }

    @Provides
    fun providesUserNameDao(db: AppDatabase): UserNameDAO {
        return db.userNameDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(dao: UserNameDAO): UserRepository {
        return UserRepositoryImpl(dao)
    }

    //Send or receive UserRepository
    @Provides
    @Singleton
    fun provideUserDetailsRepository(dao: UserNameDAO): com.fyndr.fileshare.domain.send_or_receive.repository.UserRepository {
        return com.fyndr.fileshare.data.send_or_receive.repository.UserRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun providesSaveUserNameUseCase(repository: UserRepository): SaveUserNameUseCase {
        return SaveUserNameUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserNameUseCase(repository: UserRepository): GetUserNameUseCase {
        return GetUserNameUseCase(repository)
    }

    // Send or Receive GetUserDetailsUseCase

    @Provides
    @Singleton
    fun provideGetUserDetailsUseCase(repository: com.fyndr.fileshare.domain.send_or_receive.repository.UserRepository): GetUserDetailsUseCase {
        return GetUserDetailsUseCase(repository)
    }


    @Provides
    fun provideNamingUseCases(
        getUserNameUseCase: GetUserNameUseCase,
        saveUserNameUseCase: SaveUserNameUseCase,
    ): NamingUseCases = NamingUseCases (
        getUserNameUseCase = getUserNameUseCase,
        saveUserNameUseCase = saveUserNameUseCase,
    )

}