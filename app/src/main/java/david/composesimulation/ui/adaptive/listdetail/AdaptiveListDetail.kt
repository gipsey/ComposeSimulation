@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package david.composesimulation.ui.adaptive.listdetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldScope
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AdaptiveListDetail() {
    val navigator = rememberListDetailPaneScaffoldNavigator()

    BackHandler(enabled = navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = { ListPane(navigator) },
        detailPane = { DetailPane() },
        extraPane = { ExtraPane() },
        modifier = Modifier
    )
}

@Composable
private fun ThreePaneScaffoldScope.ListPane(navigator: ThreePaneScaffoldNavigator<Nothing>) {
    println("AdaptiveMain - ListPane")
    AnimatedPane {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green)
        ) {
            Text(
                text = "ListPane",
            )
            Button(
                onClick = { navigator.navigateTo(pane = ThreePaneScaffoldRole.Primary) },
            ) {
                Text(
                    text = "To detail",
                )
            }
        }
    }
}

@Composable
private fun ThreePaneScaffoldScope.DetailPane() {
    println("AdaptiveMain - DetailPane")
    AnimatedPane {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        ) {
            Text(
                text = "DetailPane",
            )
        }
    }
}

@Composable
private fun ThreePaneScaffoldScope.ExtraPane() {
    println("AdaptiveMain - ExtraPane")
    AnimatedPane {

    }
}

@Preview
@Composable
fun AdaptiveListDetailPreview() {
    AdaptiveListDetail()
}
