package com.mayad7474.cleane_commerce.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mayad7474.cleane_commerce.feature.home.ui.HomeScreen
import com.mayad7474.cleane_commerce.R
import com.mayad7474.cleane_commerce.feature.settings.ui.SettingsScreen
import com.mayad7474.cleane_commerce.core.components.LottieBottomNavigationBar
import com.mayad7474.cleane_commerce.feature.cart.ui.CartScreen
import com.mayad7474.cleane_commerce.feature.wishlist.ui.WishlistScreen

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val items = listOf(
        BottomNavItem("Home", R.raw.home),
        BottomNavItem("Cart", R.raw.cart),
        BottomNavItem("Wishlist", R.raw.wishlist),
        BottomNavItem("Settings", R.raw.settings)
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            LottieBottomNavigationBar(
                items = items,
                currentItem = items[selectedIndex],
                onItemSelected = { selectedIndex = items.indexOf(it) }
            )
        }
    ) { innerPadding ->
        when (selectedIndex) {
            0 -> HomeScreen(Modifier.padding(innerPadding))
            1 -> CartScreen(Modifier.padding(innerPadding))
            2 -> WishlistScreen(Modifier.padding(innerPadding))
            3 -> SettingsScreen(Modifier.padding(innerPadding))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MaterialTheme {
        MainScreen()
    }
}
