package david.composesimulation.ui.launchedeffect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import david.composesimulation.ui.theme.ComposeSimulationTheme
import timber.log.Timber

@Composable
fun SnackbarWithLaunchedEffect(content: String, elapsedSeconds: Int) {
    Timber.d("SnackbarWithLaunchedEffect - elapsedSeconds $elapsedSeconds")
    val snackbarHostState = remember { SnackbarHostState() }

    val showSnackbarTrigger = remember { mutableStateOf<Any?>(null) }
    LaunchedEffect(showSnackbarTrigger.value) {
        showSnackbarTrigger.value?.let {
            snackbarHostState.showSnackbar("Nothing to handle on this text :)")
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .clickable {
                    showSnackbarTrigger.value = Any()
                }
        ) {
            RelevantContent(content)

            Text(
                text = elapsedSeconds.toString(),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun BoxScope.RelevantContent(content: String) {
    Text(
        text = content,
        modifier =
        Modifier
            .padding(top = 24.dp)
            .padding(horizontal = 24.dp)
            .align(Alignment.TopStart)
            .verticalScroll(rememberScrollState())
    )
}

@Preview(showSystemUi = true)
@Composable
fun SnackbarWithLaunchedEffectPreview() {
    ComposeSimulationTheme {
        SnackbarWithLaunchedEffect("body", 12)
    }
}
