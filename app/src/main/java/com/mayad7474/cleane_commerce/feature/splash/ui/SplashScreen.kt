package com.mayad7474.cleane_commerce.feature.splash.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mayad7474.cleane_commerce.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateNext: () -> Unit
) {
    // Animation states
    val logoScale = remember { Animatable(0.8f) }
    val titleChars = "Welcome to ShopEase".toList()
    val titleAlpha = remember { mutableStateListOf<Float>().apply { repeat(titleChars.size) { add(0f) } } }
    val descriptionOffset = remember { Animatable(100f) }
    val descriptionAlpha = remember { Animatable(0f) }
    val slideOutOffset = remember { Animatable(0f) }

    // Trigger animations sequentially
    LaunchedEffect(Unit) {
        // Scale the logo
        logoScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
        )
        delay(100) // Delay before showing the text

        // Animate each character in the title
        titleChars.forEachIndexed { index, _ ->
            titleAlpha[index] = 1f
            delay(30) // Delay between characters
        }

        delay(100) // Delay before showing the description

        // Slide-in and fade-in description
        descriptionOffset.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
        )
        descriptionAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 400, easing = LinearEasing)
        )

        delay(1200)

        // Slide-out animation before navigating
        slideOutOffset.animateTo(
            targetValue = -1000f,
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )

        // Navigate to the next screen
        onNavigateNext()
    }

    // UI Layout
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        // Background image
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Content Column
        Column(
            modifier = Modifier
                .offset(y = slideOutOffset.value.dp), // Slide-out animation
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo with scaling animation
            Image(
                painter = painterResource(R.drawable.always_online),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .graphicsLayer(
                        scaleX = logoScale.value,
                        scaleY = logoScale.value
                    )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title with character-by-character fade-in animation
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                titleChars.forEachIndexed { index, char ->
                    Text(
                        text = char.toString(),
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = Color.White,
                        modifier = Modifier.alpha(titleAlpha[index])
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description with slide-in and fade-in animation
            Text(
                text = "Discover the best deals and products just for you.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .offset(y = descriptionOffset.value.dp)
                    .alpha(descriptionAlpha.value)
            )
        }
    }
}


