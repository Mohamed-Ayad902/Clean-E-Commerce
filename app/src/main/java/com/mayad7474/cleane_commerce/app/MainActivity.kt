package com.mayad7474.cleane_commerce.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mayad7474.cleane_commerce.navigation.MainNavigation
import com.mayad7474.cleane_commerce.app.theme.CleanECommerceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanECommerceTheme {
                MainNavigation()
            }
        }
    }
}