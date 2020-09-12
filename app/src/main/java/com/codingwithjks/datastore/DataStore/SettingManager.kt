package com.codingwithjks.datastore.DataStore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.codingwithjks.datastore.enum.UiMode
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.intellij.lang.annotations.Flow
import java.io.IOException

class SettingManager(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "setting")

    companion object {
        val Is_Dark_Mode = preferencesKey<Boolean>("dark_mode")
    }

    suspend fun setUiMode(uiMode: UiMode)
    {
        dataStore.edit {preference->
            preference[Is_Dark_Mode]=when(uiMode)
            {
                UiMode.LIGHT->false
                UiMode.DARK->true
            }
        }
    }
    val uiModeFlow: kotlinx.coroutines.flow.Flow<UiMode> = dataStore.data
        .catch {
            if(it is IOException)
            {
                it.printStackTrace()
                emit(emptyPreferences())
            }else{
                throw it
            }
        }
        .map {preference->
            when(preference[Is_Dark_Mode]?: false)
            {
                true->UiMode.DARK
                false->UiMode.LIGHT
            }
        }



 }