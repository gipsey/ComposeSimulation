package david.composesimulation.ui.derivedstateof

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import david.composesimulation.ui.theme.ComposeSimulationTheme
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun WronglyContentWithMoveToTop(content: String) {
    Timber.d("WronglyContentWithMoveToTop")
    val scrollState = rememberScrollState()

    val moveToTopEnabled = scrollState.value > 100

    Timber.d("WronglyContentWithMoveToTop Content")
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton =
        if (moveToTopEnabled) {
            {
                SmallFloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            scrollState.animateScrollTo(0)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = null
                    )
                }
            }
        } else {
            {}
        }
    ) { paddingValues ->
        Text(
            text = content,
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun WronglyContentWithMoveToTopPreview() {
    ComposeSimulationTheme {
        WronglyContentWithMoveToTop("body")
    }
}
