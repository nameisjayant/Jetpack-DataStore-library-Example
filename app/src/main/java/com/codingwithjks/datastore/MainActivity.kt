package com.codingwithjks.datastore

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.codingwithjks.datastore.DataStore.SettingManager
import com.codingwithjks.datastore.enum.UiMode
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var settingManager: SettingManager
    private var dark=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingManager= SettingManager(applicationContext)

        intiViews()
        observeUiPreferences()
    }

    private fun observeUiPreferences() {
        settingManager.uiModeFlow.asLiveData().observe(this){
            when(it)
            {
                UiMode.DARK->onDarkMode()
                UiMode.LIGHT->onLightMode()
            }
        }
    }

    private fun onLightMode() {
      dark=false
        rootView.setBackgroundColor(ContextCompat.getColor(this,R.color.light))
       button.text="Dark"
    }

    private fun onDarkMode() {
        dark=true
        rootView.setBackgroundColor(ContextCompat.getColor(this,R.color.dark))
        button.text="Light"
    }

    private fun intiViews() {
        button.setOnClickListener {
            lifecycleScope.launch {
                when (dark) {
                    true -> settingManager.setUiMode(UiMode.LIGHT)
                    false -> settingManager.setUiMode(UiMode.DARK)
                }
            }
        }
    }
}