package me.amryousef.fpl.ui.screens.main

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.amryousef.fpl.LocalServiceProvider
import me.amryousef.fpl.ServiceLoader
import me.amryousef.fpl.ui.screens.login.LoginScreen
import me.amryousef.fpl.ui.theme.FPLTheme

@Composable
fun MainScreen(
    application: Application
) {
    CompositionLocalProvider(
        LocalServiceProvider.provides(
            ServiceLoader(application)
        )
    ) {
        val stateStore = appContext()
        val navController = rememberNavController()
        FPLTheme {
            Scaffold(
                bottomBar = {
                    if (stateStore.state.value.isLoggedIn) {
                        BottomNavigation {
                            BottomNavigationItem(
                                selected = false,
                                onClick = { navController.navigate("team") },
                                label = { Text(text = "Team") },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Home,
                                        contentDescription = "Team"
                                    )
                                }
                            )
                        }
                    }
                }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = if (stateStore.state.value.isLoggedIn) {
                        "team"
                    } else {
                        "login"
                    }
                ) {
                    composable("login") {
                        LoginScreen(
                            modifier = Modifier.fillMaxSize(),
                            onLoginComplete = { stateStore.onLoginComplete() }
                        )
                    }
                    composable("team") {
                        Text(text = "Team")
                    }
                }
            }
        }
    }
}