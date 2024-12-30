package com.mayad7474.cleane_commerce.feature.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onRegisterCLicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onLoginClicked: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login Screen", modifier = Modifier.clickable {
            onLoginClicked()
        })
        Text(text = "Register Screen", modifier = Modifier.clickable {
            onRegisterCLicked()
        })
        Text(text = "Forgot Password", modifier = Modifier.clickable {
            onForgotPasswordClicked()
        })
    }
}