package com.fyndr.fileshare.data.datamanager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.fyndr.fileshare.domain.datamanager.LocalUserManager
import com.fyndr.fileshare.utils.Constants.FYLESHARE_DATASTORE
import com.fyndr.fileshare.utils.Constants.FYLESHARE_ONBOARDING_COMPLETED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImpl(
    private val context: Context
): LocalUserManager {
    override suspend fun saveOnBoardingState(value: Boolean) {
        context.fyleShareDataStore.edit { prefs ->
            prefs[PreferencesKeys.ON_BOARDING_VALUE] = value
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return context.fyleShareDataStore.data.map { prefs ->
            prefs[PreferencesKeys.ON_BOARDING_VALUE] ?: false
        }
    }


}


private val Context.fyleShareDataStore: DataStore<Preferences> by preferencesDataStore(name = FYLESHARE_DATASTORE)


private object PreferencesKeys {
    val ON_BOARDING_VALUE = booleanPreferencesKey(name = FYLESHARE_ONBOARDING_COMPLETED)
}

