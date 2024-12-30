@file:OptIn(ExperimentalMaterial3Api::class)

package com.mayad7474.cleane_commerce.core.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mayad7474.cleane_commerce.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


private const val maxHeight = 180

enum class RefreshIndicatorState(val messageRes: String) {
    Default("default"),
    PullingDown("Pull to refresh"),
    ReachedThreshold("Refreshing.."),
    Refreshing("refreshing - state")
}

@Composable
fun PullToRefreshIndicator(
    modifier: Modifier = Modifier,
    indicatorState: RefreshIndicatorState,
    pullToRefreshProgress: Float,
    timeElapsed: String,
) {
    val heightModifier = when (indicatorState) {
        RefreshIndicatorState.PullingDown -> Modifier.height(
            (pullToRefreshProgress * maxHeight)
                .roundToInt()
                .coerceAtMost(maxHeight).dp
        )
        RefreshIndicatorState.ReachedThreshold -> Modifier.height(maxHeight.dp)
        RefreshIndicatorState.Refreshing -> Modifier.wrapContentHeight()
        RefreshIndicatorState.Default -> Modifier.height(0.dp)
    }

    println("PullToRefreshIndicator-> $indicatorState")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .then(heightModifier)
            .padding(15.dp),
        contentAlignment = Alignment.Center,
    ) {
        CurvedShapeBackground(
            progress = pullToRefreshProgress
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = indicatorState.messageRes,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (indicatorState == RefreshIndicatorState.ReachedThreshold) {
                    LottieRefreshIndicator()
                } else {
                    Text(
                        text = "last refreshed: $timeElapsed",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}

@Composable
fun CurvedShapeBackground(
    progress: Float,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height((progress * 170).coerceAtMost(170f).dp)
            .clip(CurvedShape(progress))
            .background(MaterialTheme.colorScheme.surfaceContainer),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

class CurvedShape(private val progress: Float) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val curveHeight = size.height
            val curveWidth = size.width

            moveTo(0f, curveHeight) // Start at the bottom-left corner
            quadraticTo(
                curveWidth / 2, // Control point X
                curveHeight * (1f - progress * 0.4f), // Control point Y (dynamic curve depth)
                curveWidth, // End point X
                curveHeight // End point Y
            )
            lineTo(curveWidth, 0f) // Top-right corner
            lineTo(0f, 0f) // Top-left corner
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun PullToRefreshLayout(
    modifier: Modifier = Modifier,
    pullRefreshLayoutState: PullToRefreshLayoutState,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
) {
    val refreshIndicatorState by pullRefreshLayoutState.refreshIndicatorState
    val timeElapsedSinceLastRefresh by pullRefreshLayoutState.lastRefreshText

    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(key1 = pullToRefreshState.distanceFraction) {
        when {
            pullToRefreshState.distanceFraction >= 1 -> {
                pullRefreshLayoutState.updateRefreshState(RefreshIndicatorState.ReachedThreshold)
            }

            pullToRefreshState.distanceFraction > 0 -> {
                pullRefreshLayoutState.updateRefreshState(RefreshIndicatorState.PullingDown)
            }

            else -> {
                pullRefreshLayoutState.updateRefreshState(RefreshIndicatorState.Default)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .pullToRefresh(
                isRefreshing = refreshIndicatorState == RefreshIndicatorState.Refreshing,
                threshold = 120.dp,
                state = pullToRefreshState,
                onRefresh = {
                    onRefresh()
                    pullRefreshLayoutState.refresh()
                }
            ),
    ) {

        PullToRefreshIndicator(
            indicatorState = refreshIndicatorState,
            pullToRefreshProgress = pullToRefreshState.distanceFraction,
            timeElapsed = timeElapsedSinceLastRefresh,
        )
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}

class PullToRefreshLayoutState(
    val onTimeUpdated: (Long) -> String,
) {

    private val _lastRefreshTime: MutableStateFlow<Long> =
        MutableStateFlow(System.currentTimeMillis())

    var refreshIndicatorState = mutableStateOf(RefreshIndicatorState.Default)
        private set

    var lastRefreshText = mutableStateOf("")
        private set

    fun updateRefreshState(refreshState: RefreshIndicatorState) {
        val now = System.currentTimeMillis()
        val timeElapsed = now - _lastRefreshTime.value
        lastRefreshText.value = onTimeUpdated(timeElapsed)
        refreshIndicatorState.value = refreshState
    }

    fun refresh() {
        _lastRefreshTime.value = System.currentTimeMillis()
        updateRefreshState(RefreshIndicatorState.Refreshing)
        // Simulate refresh completion
        // TODO: remove this
        GlobalScope.launch {
            kotlinx.coroutines.delay(3000) // Simulate 3 seconds
            updateRefreshState(RefreshIndicatorState.Default)
        }
    }

}

@Composable
fun rememberPullToRefreshState(
    onTimeUpdated: (Long) -> String,
): PullToRefreshLayoutState =
    remember {
        PullToRefreshLayoutState(onTimeUpdated)
    }

@Composable
fun LottieRefreshIndicator(){
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.third_animation))
        val mProgress by animateLottieCompositionAsState(composition, iterations = 5)

        LottieAnimation(
            composition = composition,
            progress = { mProgress },
        )
    }
}