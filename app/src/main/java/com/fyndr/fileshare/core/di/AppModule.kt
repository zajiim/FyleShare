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
import com.fyndr.fileshare.data.send_or_receive.repository.NearByRepositoryImpl
import com.fyndr.fileshare.data.transfer.local.dao.FileTransferDAO
import com.fyndr.fileshare.data.transfer.repository.FileTransferRepositoryImpl
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
import com.fyndr.fileshare.domain.send_or_receive.repository.NearByRepository
import com.fyndr.fileshare.domain.send_or_receive.use_cases.ConnectToDeviceUseCase
import com.fyndr.fileshare.domain.send_or_receive.use_cases.DisconnectUseCase
import com.fyndr.fileshare.domain.send_or_receive.use_cases.GetUserDetailsUseCase
import com.fyndr.fileshare.domain.send_or_receive.use_cases.NearbyUseCases
import com.fyndr.fileshare.domain.send_or_receive.use_cases.RequestPermissionsUseCase
import com.fyndr.fileshare.domain.send_or_receive.use_cases.SendFileUseCase
import com.fyndr.fileshare.domain.send_or_receive.use_cases.StartAdvertisingUseCase
import com.fyndr.fileshare.domain.send_or_receive.use_cases.StartDiscoveryUseCase
import com.fyndr.fileshare.domain.send_or_receive.use_cases.StopAdvertisingUseCase
import com.fyndr.fileshare.domain.send_or_receive.use_cases.StopDiscoveryUseCase
import com.fyndr.fileshare.domain.transfer.repository.FileTransferRepository
import com.fyndr.fileshare.domain.transfer.use_cases.FileTransferUseCases
import com.fyndr.fileshare.domain.transfer.use_cases.GetFileTransferHistoryUseCase
import com.fyndr.fileshare.domain.transfer.use_cases.SaveFileTransferHistoryUseCase
import com.fyndr.fileshare.domain.transfer.use_cases.UpdateFileTransferUseCase
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
    fun providesFileTransferDao(db: AppDatabase): FileTransferDAO {
        return db.fileTransferDao()
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

    //Nearby Repository
    @Provides
    @Singleton
    fun provideNearbyRepository(@ApplicationContext context: Context): NearByRepository {
        return NearByRepositoryImpl(context)
    }


    // File Transfer Repository
    @Provides
    @Singleton
    fun provideFileTransferRepository(dao: FileTransferDAO): FileTransferRepository {
        return FileTransferRepositoryImpl(dao)
    }

    // Nearby Use Cases
    @Provides
    fun provideStartAdvertisingUseCase(repository: NearByRepository): StartAdvertisingUseCase {
        return StartAdvertisingUseCase(repository)
    }

    @Provides
    fun provideStopAdvertisingUseCase(repository: NearByRepository): StopAdvertisingUseCase {
        return StopAdvertisingUseCase(repository)
    }

    @Provides
    fun provideStartDiscoveryUseCase(repository: NearByRepository): StartDiscoveryUseCase {
        return StartDiscoveryUseCase(repository)
    }

    @Provides
    fun provideStopDiscoveryUseCase(repository: NearByRepository): StopDiscoveryUseCase {
        return StopDiscoveryUseCase(repository)
    }

    @Provides
    fun provideConnectToDeviceUseCase(repository: NearByRepository): ConnectToDeviceUseCase {
        return ConnectToDeviceUseCase(repository)
    }

    @Provides
    fun provideDisconnectUseCase(repository: NearByRepository): DisconnectUseCase {
        return DisconnectUseCase(repository)
    }

    @Provides
    fun provideSendFileUseCase(repository: NearByRepository): SendFileUseCase {
        return SendFileUseCase(repository)
    }

    @Provides
    fun provideRequestPermissionsUseCase(repository: NearByRepository): RequestPermissionsUseCase {
        return RequestPermissionsUseCase(repository)
    }

    @Provides
    fun provideNearbyUseCases(
        startAdvertising: StartAdvertisingUseCase,
        stopAdvertising: StopAdvertisingUseCase,
        startDiscovery: StartDiscoveryUseCase,
        stopDiscovery: StopDiscoveryUseCase,
        connectToDevice: ConnectToDeviceUseCase,
        disconnect: DisconnectUseCase,
        sendFile: SendFileUseCase,
        requestPermissions: RequestPermissionsUseCase
    ): NearbyUseCases {
        return NearbyUseCases(
            startAdvertisingUseCase = startAdvertising,
            stopAdvertisingUseCase = stopAdvertising,
            startDiscoveryUseCase = startDiscovery,
            stopDiscoveryUseCase = stopDiscovery,
            connectToDeviceUseCase = connectToDevice,
            disconnectUseCase = disconnect,
            sendFileUseCase = sendFile,
            requestPermissionsUseCase = requestPermissions,
        )
    }


    // Transfer Use Cases
    @Provides
    fun provideGetTransferHistoryUseCase(repository: FileTransferRepository): GetFileTransferHistoryUseCase {
        return GetFileTransferHistoryUseCase(repository)
    }

    @Provides
    fun provideSaveTransferUseCase(repository: FileTransferRepository): SaveFileTransferHistoryUseCase {
        return SaveFileTransferHistoryUseCase(repository)
    }

    @Provides
    fun provideUpdateTransferUseCase(repository: FileTransferRepository): UpdateFileTransferUseCase {
        return UpdateFileTransferUseCase(repository)
    }

    @Provides
    fun provideTransferUseCases(
        getTransferHistory: GetFileTransferHistoryUseCase,
        saveTransfer: SaveFileTransferHistoryUseCase,
        updateTransfer: UpdateFileTransferUseCase
    ): FileTransferUseCases {
        return FileTransferUseCases(
            getFileTransferHistory = getTransferHistory,
            saveFileTransfer = saveTransfer,
            updateFileTransfer = updateTransfer
        )
    }



}