package me.amryousef.fpl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import me.amryousef.fpl.ui.screens.login.LoginScreen
import me.amryousef.fpl.ui.screens.main.MainScreen
import me.amryousef.fpl.ui.theme.FPLTheme

val LocalServiceProvider = staticCompositionLocalOf {
    ServiceLoader()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(application)
        }
    }
}