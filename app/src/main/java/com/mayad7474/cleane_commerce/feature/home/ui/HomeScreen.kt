package com.mayad7474.cleane_commerce.feature.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mayad7474.cleane_commerce.core.components.PullToRefreshLayout
import com.mayad7474.cleane_commerce.core.components.rememberPullToRefreshState

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val pullToRefreshState = rememberPullToRefreshState { timeElapsed ->
        // Convert elapsed time to a readable format
        val seconds = timeElapsed / 1000
        if (seconds < 60) "$seconds seconds ago" else "${seconds / 60} minutes ago"
    }

    PullToRefreshLayout(
        modifier = modifier,
        pullRefreshLayoutState = pullToRefreshState,
        onRefresh = {
            println("Refreshing content...")
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(20) { index ->
                Text(
                    text = "Item $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
