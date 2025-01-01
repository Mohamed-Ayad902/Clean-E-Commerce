package com.mayad7474.cleane_commerce.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mayad7474.cleane_commerce.core.constants.Drawables
import com.mayad7474.cleane_commerce.core.utils.PaddingSize

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
    leadingIcon: (@Composable (() -> Unit))? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    textStyle: androidx.compose.ui.text.TextStyle = LocalTextStyle.current,
    colors: TextFieldColors =
        TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            disabledContainerColor = Color.Gray,
            errorContainerColor = Color.Red,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label, color = MaterialTheme.colorScheme.secondary) },
            leadingIcon = leadingIcon,
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            modifier = Modifier.size(PaddingSize.Large.toDp()),
                            painter = if (passwordVisible) painterResource(Drawables.password_showing)
                            else painterResource(Drawables.password_not_showing),
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    trailingIcon?.invoke()
                }
            },
            isError = isError,
            keyboardOptions = if (isPassword) {
                keyboardOptions.copy(keyboardType = KeyboardType.Password)
            } else {
                keyboardOptions
            },
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            textStyle = textStyle,
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            colors = colors,
            shape = RoundedCornerShape(PaddingSize.Medium.toDp()),
            modifier = Modifier
                .fillMaxWidth()
        )

        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(
                    start = PaddingSize.Medium.toDp(),
                    top = PaddingSize.Medium.toDp()
                )
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun TextInputPreview() {
    var text by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    TextInput(
        value = text,
        onValueChange = {
            text = it
            isError = it.isEmpty()
            errorMessage = if (it.isEmpty()) "Field cannot be empty" else ""
        },
        label = "Username",
        isError = isError,
        errorMessage = errorMessage,
        leadingIcon = {
            Icon(
                painter = painterResource(Drawables.logo),
                contentDescription = "User Icon",
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear Icon",
                modifier = Modifier.size(24.dp)
            )
        }
    )
}