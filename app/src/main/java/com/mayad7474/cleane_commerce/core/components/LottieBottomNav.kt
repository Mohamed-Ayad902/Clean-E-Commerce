package com.mayad7474.cleane_commerce.core.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.mayad7474.cleane_commerce.navigation.BottomNavItem
import com.mayad7474.cleane_commerce.app.theme.Selected
import com.mayad7474.cleane_commerce.app.theme.Unselected
import kotlinx.coroutines.launch

@Composable
fun LottieBottomNavigationBar(
    items: List<BottomNavItem>,
    currentItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp)
            )
            .padding(vertical = 8.dp)
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            LottieBottomNavItem(
                item = item,
                isSelected = item == currentItem,
                onClick = { onItemSelected(item) }
            )
        }
    }
}

@Composable
fun LottieBottomNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(item.lottieRes))
    val progress = rememberLottieAnimatable()
    val scope = rememberCoroutineScope()

    val boxSize by animateDpAsState(targetValue = if (isSelected) 55.dp else 50.dp, label = "boxSize")
    val iconScale by animateFloatAsState(targetValue = if (isSelected) 1.2f else 1f, label = "boxSize")
    val cornerRadius by animateDpAsState(targetValue = if (isSelected) 20.dp else 16.dp, label = "cornerRadius")
    val textAlpha by animateFloatAsState(targetValue = if (isSelected) 1f else 0.2f, label = "textAlpha")

    var animatedText by remember { mutableStateOf("") }
    LaunchedEffect(isSelected) {
        if (isSelected) {
            animatedText = ""
            item.name.forEachIndexed { _, char ->
                animatedText += char
                kotlinx.coroutines.delay(50)
            }
        } else {
            animatedText = ""
        }
    }

    Column(
        modifier = Modifier
            .height(80.dp)
            .clickable {
                onClick()
                scope.launch { progress.animate(composition = composition, iterations = 1) }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(boxSize)
                .clip(RoundedCornerShape(cornerRadius))
                .background(if (isSelected) Selected else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            val dynamicProperties = rememberLottieDynamicProperties(
                rememberLottieDynamicProperty(
                    property = LottieProperty.COLOR,
                    value = if (isSelected) White.toArgb() else Unselected.toArgb(),
                    keyPath = arrayOf("**")
                )
            )
            LottieAnimation(
                modifier = Modifier
                    .size(26.dp)
                    .scale(if (item.name != "Wishlist") iconScale else 1.6f),
                composition = composition,
                progress = { progress.value },
                dynamicProperties = dynamicProperties
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (animatedText.isNotEmpty()) {
            Text(
                text = animatedText,
                style = MaterialTheme.typography.bodySmall,
                color = if (isSelected) Selected else Unselected,
                modifier = Modifier.alpha(textAlpha)
            )
        }
    }
}







