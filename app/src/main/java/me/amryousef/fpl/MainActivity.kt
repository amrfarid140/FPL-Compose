package me.amryousef.fpl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.amryousef.fpl.ui.screens.LoginScreen
import me.amryousef.fpl.ui.theme.FPLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FPLTheme {
                Scaffold {
                    LoginScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}