package com.mayad7474.cleane_commerce.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.mayad7474.cleane_commerce.feature.auth.ui.ForgotPasswordScreen
import com.mayad7474.cleane_commerce.feature.auth.ui.LoginScreen
import com.mayad7474.cleane_commerce.feature.auth.ui.RegisterScreen
import com.mayad7474.cleane_commerce.feature.splash.ui.SplashScreen


@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainGraph.SplashGraph) {
        // Splash Graph
        navigation<MainGraph.SplashGraph>(
            startDestination = AuthenticationScreens.Splash,
        ) {
            composable<AuthenticationScreens.Splash> {
                SplashScreen(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                    navController.navigate(MainGraph.AuthenticationGraph) {
                        popUpTo(MainGraph.SplashGraph) { inclusive = true }
                    }
                }
            }
        }

        // Authentication Graph
        navigation<MainGraph.AuthenticationGraph>(
            startDestination = AuthenticationScreens.Login,
        ) {
            composable<AuthenticationScreens.Login> {
                LoginScreen(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    onLoginClicked = {
                        navController.navigate(MainGraph.HomeGraph) {
                            popUpTo(MainGraph.AuthenticationGraph) { inclusive = true }
                        }
                    },
                    onRegisterCLicked = {
                        navController.navigate(AuthenticationScreens.Register)
                    },
                    onForgotPasswordClicked = {
                        navController.navigate(AuthenticationScreens.ForgotPassword)
                    }
                )
            }
            composable<AuthenticationScreens.Register> {
                RegisterScreen(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    onRegisterCLicked = {
                        navController.navigate(AuthenticationScreens.Login) {
                            popUpTo(AuthenticationScreens.Register) { inclusive = true }
                        }
                    })
            }
            composable<AuthenticationScreens.ForgotPassword> {
                ForgotPasswordScreen(modifier = Modifier.background(MaterialTheme.colorScheme.background))
            }
        }

        // Home Graph with Bottom Navigation
        composable<MainGraph.HomeGraph> {
            MainScreen(modifier = Modifier.background(MaterialTheme.colorScheme.background))
        }
    }
}

