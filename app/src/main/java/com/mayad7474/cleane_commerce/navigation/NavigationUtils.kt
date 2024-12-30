package com.mayad7474.cleane_commerce.navigation

import kotlinx.serialization.Serializable

interface Screen
/*interface Screen

sealed class MainGraph {
    @Serializable
    data object SplashGraph : MainGraph()

    @Serializable
    data object AuthenticationGraph : MainGraph()

    @Serializable
    data object HomeGraph : MainGraph()
}

sealed class SplashScreens : Screen {
    @Serializable
    data object Splash : SplashScreens()
}

sealed class AuthenticationScreens : Screen {
    @Serializable
    data object Login : AuthenticationScreens()

    @Serializable
    data object Register : AuthenticationScreens()

    @Serializable
    data object ForgotPassword : AuthenticationScreens()

    companion object {
        val screens = listOf(Login, Register, ForgotPassword)
    }
}

sealed class MainScreens : Screen {
    @Serializable
    data object Home : MainScreens()

    @Serializable
    data object Cart : MainScreens()

    @Serializable
    data object Wishlist : MainScreens()

    @Serializable
    data object Profile : MainScreens()

    companion object {
        val screens = listOf(Home, Cart, Wishlist, Profile)
    }
}*/

sealed class MainGraph {
    @Serializable
    data object SplashGraph : MainGraph()

    @Serializable
    data object AuthenticationGraph : MainGraph()

    @Serializable
    data object HomeGraph : MainGraph()
}


sealed class AuthenticationScreens : Screen {
    @Serializable
    data object Splash :AuthenticationScreens()

    @Serializable
    data object Login : AuthenticationScreens()

    @Serializable
    data object Register : AuthenticationScreens()

    @Serializable
    data object ForgotPassword : AuthenticationScreens()

    @Serializable
    data object Home : AuthenticationScreens()

    @Serializable
    data object Cart : AuthenticationScreens()

    @Serializable
    data object Wishlist : AuthenticationScreens()

    @Serializable
    data object Profile : AuthenticationScreens()

}