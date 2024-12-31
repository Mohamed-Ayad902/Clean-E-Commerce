package com.mayad7474.cleane_commerce.core.extensions

import android.util.Patterns
import androidx.core.text.isDigitsOnly
import java.util.regex.Pattern

fun String.isPasswordValid(): Boolean {
    val specialPass =
        Pattern.compile("^(?=.*?[A-Z])(?=(.*[a-z])+)(?=(.*\\d)+)(?=(.*\\W)+)(?!.*\\s).{8,}\$")
    return if (this.isNotEmpty())
        specialPass.matcher(this).matches()
    else false
}

fun String.isEmailValid(): Boolean {
    if (this.isEmpty()) return false

    val mailContainCars = Patterns.EMAIL_ADDRESS.matcher(this).matches()
    val checkIfMailNumbers: Int? = this.split("@")[0].toIntOrNull()

    return when {
        mailContainCars || checkIfMailNumbers != null -> true
        else -> false
    }
}

fun String.isNumericOnly(): Boolean {
    return this.isDigitsOnly()
            || this.toDoubleOrNull() != null
            || this.toFloatOrNull() != null
}