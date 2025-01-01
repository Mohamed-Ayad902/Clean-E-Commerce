package com.mayad7474.cleane_commerce.feature.auth.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mayad7474.cleane_commerce.R
import com.mayad7474.cleane_commerce.core.components.TextInput
import com.mayad7474.cleane_commerce.core.constants.Drawables
import com.mayad7474.cleane_commerce.core.constants.Strings
import com.mayad7474.cleane_commerce.core.utils.PaddingSize
import com.mayad7474.cleane_commerce.core.utils.TextSize

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onForgotPasswordClicked: () -> Unit,
    performRegister: () -> Unit,
    performLogin: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(PaddingSize.Medium.toDp())
            .fillMaxSize()
            .animateContentSize()
            .padding(vertical = PaddingSize.Large.toDp())
            .verticalScroll(rememberScrollState())
    ) {
        var isLogin by remember { mutableStateOf(true) }
        var rememberMe by remember { mutableStateOf(false) }

        HeaderSection(isLogin)
        Spacer(Modifier.height(PaddingSize.ExtraLarge.toDp()))

        AnimatedVisibility(visible = !isLogin) { UpperFields() }

        EmailPassword()
        Spacer(Modifier.height(PaddingSize.Medium.toDp()))

        AnimatedVisibility(visible = !isLogin) { RepeatPassword() }

        Spacer(Modifier.height(PaddingSize.Medium.toDp()))

        Button(
            shape = RoundedCornerShape(PaddingSize.Medium.toDp()),
            modifier = Modifier.fillMaxWidth(),
            onClick = { if (isLogin) performLogin() else performRegister() }
        ) {
            Text(
                modifier = Modifier.padding(vertical = PaddingSize.Small.toDp()),
                fontSize = TextSize.Medium.toSp(),
                text = if (isLogin) stringResource(Strings.login) else stringResource(Strings.register)
            )
        }

        Spacer(Modifier.height(PaddingSize.Medium.toDp()))

        AccountTexts(
            isLogin = isLogin,
            forgotPasswordClicked = onForgotPasswordClicked,
            changeScreens = { isLogin = !isLogin }
        )

        Spacer(Modifier.height(PaddingSize.Medium.toDp()))

        AnimatedVisibility(visible = isLogin) { RememberMe(rememberMe) { rememberMe = it } }
    }
}


@Composable
private fun AccountTexts(
    isLogin: Boolean,
    forgotPasswordClicked: () -> Unit,
    changeScreens: () -> Unit
) {
    AnimatedVisibility(isLogin) {
        Text(
            color = MaterialTheme.colorScheme.secondary,
            text = stringResource(Strings.forgot_password),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
            fontSize = TextSize.Medium.toSp(),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { forgotPasswordClicked() }
        )
    }
    Spacer(Modifier.height(PaddingSize.Medium.toDp()))
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isLogin) stringResource(Strings.dont_have_account) else stringResource(
                Strings.already_have_account
            ),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = TextSize.Medium.toSp(),
        )
        Text(
            text = if (isLogin) stringResource(Strings.register) else stringResource(Strings.login),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = TextSize.Medium.toSp(),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = PaddingSize.Small.toDp())
                .clickable { changeScreens() }
        )
    }
}


@Composable
private fun RepeatPassword() {
    var passwordText by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    TextInput(
        isPassword = true,
        value = passwordText,
        onValueChange = {
            passwordText = it
        },
        label = stringResource(id = Strings.repeat_password),
        isError = passwordError.isNotEmpty(),
        errorMessage = passwordError,
        leadingIcon = {
            Icon(
                painter = painterResource(Drawables.lock),
                contentDescription = stringResource(id = Strings.repeat_password),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(PaddingSize.Large.toDp())
            )
        }
    )
}

@Composable
private fun EmailPassword() {
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    TextInput(
        value = emailText,
        onValueChange = {
            emailText = it
        },
        label = stringResource(id = Strings.email),
        isError = emailError.isNotEmpty(),
        errorMessage = emailError,
        leadingIcon = {
            Icon(
                painter = painterResource(Drawables.email),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(id = Strings.email),
                modifier = Modifier.size(PaddingSize.Large.toDp())
            )
        },
    )
    Spacer(Modifier.height(PaddingSize.Medium.toDp()))
    TextInput(
        isPassword = true,
        value = passwordText,
        onValueChange = {
            passwordText = it
        },
        label = stringResource(id = Strings.password),
        isError = passwordError.isNotEmpty(),
        errorMessage = passwordError,
        leadingIcon = {
            Icon(
                painter = painterResource(Drawables.lock),
                contentDescription = stringResource(id = Strings.password),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(PaddingSize.Large.toDp())
            )
        }
    )
}

@Composable
private fun RememberMe(rememberMe: Boolean, onRememberMeChanged: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(Strings.remember_me),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = TextSize.Medium.toSp(),
            fontWeight = FontWeight.Bold
        )
        Switch(
            checked = rememberMe,
            onCheckedChange = onRememberMeChanged,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
private fun HeaderSection(isLogin: Boolean) {
    val titleText = if (isLogin) stringResource(id = R.string.welcome_back)
    else stringResource(id = Strings.lets_you_in)

    val descriptionText = if (isLogin)
        stringResource(id = R.string.login_description)
    else
        stringResource(id = R.string.register_description)


    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Crossfade(targetState = isLogin, label = "fadingImage") { targetState ->
            val image = if (targetState) Drawables.login else Drawables.register
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }
    }


    Spacer(Modifier.height(PaddingSize.Huge.toDp()))
    Text(
        color = MaterialTheme.colorScheme.primary,
        text = titleText,
        fontSize = TextSize.ExtraLarge.toSp(),
        fontWeight = FontWeight.ExtraBold
    )
    Spacer(Modifier.height(PaddingSize.Small.toDp()))
    Text(
        color = MaterialTheme.colorScheme.secondary,
        text = descriptionText,
        fontSize = TextSize.Medium.toSp()
    )
}

@Composable
private fun UpperFields() {
    var usernameText by remember { mutableStateOf("") }
    var phoneText by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column {
        TextInput(
            value = usernameText,
            onValueChange = { usernameText = it },
            label = stringResource(id = Strings.username),
            isError = isError,
            errorMessage = errorMessage,
            leadingIcon = {
                Icon(
                    painter = painterResource(Drawables.user),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(id = Strings.username),
                    modifier = Modifier.size(PaddingSize.Large.toDp())
                )
            },
        )
        Spacer(Modifier.height(PaddingSize.Medium.toDp()))
        TextInput(
            value = phoneText,
            onValueChange = { phoneText = it },
            label = stringResource(id = Strings.phone),
            isError = isError,
            errorMessage = errorMessage,
            leadingIcon = {
                Icon(
                    painter = painterResource(Drawables.phone),
                    contentDescription = stringResource(id = Strings.phone),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(PaddingSize.Large.toDp())
                )
            },
        )
        Spacer(Modifier.height(PaddingSize.Medium.toDp()))
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        onForgotPasswordClicked = {},
        performLogin = {},
        performRegister = {}
    )
}