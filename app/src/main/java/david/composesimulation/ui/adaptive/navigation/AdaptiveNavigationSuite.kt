@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package david.composesimulation.ui.adaptive.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

enum class NavigationItem {
    Home,
    Settings,
}

@Composable
fun AdaptiveNavigationSuite() {
    var selectedDestination by rememberSaveable { mutableStateOf(NavigationItem.Home) }

    NavigationSuiteScaffold(
        layoutType = NavigationSuiteType.NavigationDrawer,
        navigationSuiteItems = {
            NavigationItem.entries.forEach { item ->
                item(
                    selected = item == selectedDestination,
                    onClick = { selectedDestination = item },
                    icon = {},
                    label = { Text(text = item.name) },
                )
            }
        }
    ) {
        when (selectedDestination) {
            NavigationItem.Home -> Home()
            NavigationItem.Settings -> Settings()
        }
    }
}

@Composable
private fun Home() {
    println("Home")
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = NavigationItem.Home.name,
        )
    }
}

@Composable
private fun Settings() {
    println("Settings")
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = NavigationItem.Settings.name,
        )
    }
}

@Preview
@Composable
fun AdaptiveNavigationSuitePreview() {
    AdaptiveNavigationSuite()
}
