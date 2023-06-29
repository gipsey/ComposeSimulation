package david.composesimulation.ui.derivedstateof

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import david.composesimulation.ui.theme.ComposeSimulationTheme
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("LogNotTimber")
@Composable
fun CorrectlyContentWithMoveToTop(content: String) {
    Timber.d("CorrectlyContentWithMoveToTop")
    val scrollState = rememberScrollState()

    val moveToTopEnabledState =
        remember {
            derivedStateOf {
                scrollState.value
                    .apply {
                        Log.d(
                            "CorrectlyContentWithMoveToTop",
                            "derivedStateOf scrollState.value $this"
                        )
                    } > 100
            }
        }

    Timber.d("CorrectlyContentWithMoveToTop Content")
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton =
        if (moveToTopEnabledState.value) {
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
fun CorrectlyContentWithMoveToTopPreview() {
    ComposeSimulationTheme {
        CorrectlyContentWithMoveToTop("body")
    }
}
