package david.composesimulation.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import david.composesimulation.MainViewModel
import david.composesimulation.description
import david.composesimulation.ui.boxWithConstraints.BoxWithConstraints1
import david.composesimulation.ui.derivedstateof.CorrectlyContentWithMoveToTop
import david.composesimulation.ui.derivedstateof.WrongEmailWithEndOfEmailReachedListener
import david.composesimulation.ui.derivedstateof.WronglyContentWithMoveToTop
import david.composesimulation.ui.gesture.AnchoredDraggable
import david.composesimulation.ui.gesture.DraggableOfEitherHorizontalOrVertical
import david.composesimulation.ui.gesture.DraggableOfHorizontalAndVertical
import david.composesimulation.ui.intrinsic.Intrinsic1
import david.composesimulation.ui.intrinsic.Intrinsic2
import david.composesimulation.ui.launchedeffect.LaunchedEffectEmailWithMarkAsRead
import david.composesimulation.ui.launchedeffect.ShowRelevantContentScreenAfterDelay
import david.composesimulation.ui.launchedeffect.SnackbarThatGetsMessageFromOutside
import david.composesimulation.ui.launchedeffect.SnackbarWithLaunchedEffect
import david.composesimulation.ui.layout.Layout1
import david.composesimulation.ui.layoutmodifier.LayoutModifier1
import david.composesimulation.ui.producestate.CorrectlyProduceStateWithContinuousEurRate
import david.composesimulation.ui.producestate.WronglyProduceStateWithContinuousEurRateUsingLaunchedEffect
import david.composesimulation.ui.remembercoroutinescope.SnackbarWithCoroutineScope
import david.composesimulation.ui.rememberupdatedstate.CorrectlyShowElapsedSecondsInSnackbarAfterDelay
import david.composesimulation.ui.rememberupdatedstate.WronglyShowElapsedSecondsInSnackbarAfterDelay
import david.composesimulation.ui.state.CorrectlyChangingState
import david.composesimulation.ui.state.WronglyChangingStateWithoutRemember
import david.composesimulation.ui.state.WronglyChangingStateWithoutRememberAndMutableState
import david.composesimulation.ui.subcompose.MultiItemPagerUseCase
import david.composesimulation.ui.subcompose.SubCompose1
import david.composesimulation.ui.subcompose.SubCompose2
import david.composesimulation.ui.theme.ComposeSimulationTheme
import kotlinx.coroutines.delay

@Composable
fun MainContent(viewModel: MainViewModel) {
    ComposeSimulationTheme {
        val navController = rememberNavController()
        val destinations = getDestinations(viewModel)

        NavHost(
            navController = navController,
            startDestination = "Menu"
        ) {
            composable("Menu") {
                Menu(navController, destinations)
            }
            destinations.forEach { (key, content) ->
                composable(route = key, content = content)
            }
        }
    }
}

@Composable
private fun Menu(
    navController: NavHostController,
    destinations: Map<String, @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(destinations.keys.toList()) {
            Button(
                onClick = { navController.navigate(it) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

private fun getDestinations(viewModel: MainViewModel) =
    mutableMapOf<String, @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit>().apply {
        put("DraggableOfEitherHorizontalOrVertical") {
            DraggableOfEitherHorizontalOrVertical()
        }
        put("DraggableOfHorizontalAndVertical") {
            DraggableOfHorizontalAndVertical()
        }
        put("AnchoredDraggable") {
            AnchoredDraggable()
        }

        put("LayoutModifier1") {
            LayoutModifier1()
        }
        put("Layout1") {
            Layout1()
        }
        put("Intrinsic1") {
            Intrinsic1()
        }
        put("Intrinsic2") {
            Intrinsic2()
        }
        put("SubCompose1") {
            SubCompose1()
        }
        put("SubCompose2") {
            SubCompose2()
        }
        put("MultiItemPagerUseCase") {
            MultiItemPagerUseCase()
        }
        put("BoxWithConstraints1") {
            BoxWithConstraints1()
        }


        put("WronglyChangingStateWithoutRememberAndMutableState") {
            WronglyChangingStateWithoutRememberAndMutableState()
        }
        put("WronglyChangingStateWithoutRemember") {
            WronglyChangingStateWithoutRemember()
        }
        put("CorrectlyChangingState") {
            CorrectlyChangingState()
        }


        put("SnackbarWithCoroutineScope") {
            val elapsedSeconds = viewModel.elapsedSeconds.collectAsStateWithLifecycle().value
            SnackbarWithCoroutineScope(description, elapsedSeconds)
        }
        put("CorrectlyContentWithMoveToTop1") {
            CorrectlyContentWithMoveToTop(description)
        }


        put("SnackbarThatGetsMessageFromOutside") {
            val messageToDisplay = remember { mutableStateOf<String?>(null) }
            LaunchedEffect(Unit) {
                delay(3000)
                messageToDisplay.value = "Don't forget to check out other articles, as well"
            }
            val elapsedSeconds = viewModel.elapsedSeconds.collectAsStateWithLifecycle().value
            SnackbarThatGetsMessageFromOutside(description, messageToDisplay.value, elapsedSeconds)
        }
        put("SnackbarWithLaunchedEffect") {
            val elapsedSeconds = viewModel.elapsedSeconds.collectAsStateWithLifecycle().value
            SnackbarWithLaunchedEffect(description, elapsedSeconds)
        }
        put("LaunchedEffectEmailWithMarkAsRead") {
            val elapsedSeconds = viewModel.elapsedSeconds.collectAsStateWithLifecycle().value
            LaunchedEffectEmailWithMarkAsRead(description, elapsedSeconds) {
                Toast.makeText(it, "Email marked as read", Toast.LENGTH_SHORT).show()
            }
        }
        put("ShowContentScreenAfterDelay") {
            val elapsedSeconds = viewModel.elapsedSeconds.collectAsStateWithLifecycle().value
            ShowRelevantContentScreenAfterDelay(description, elapsedSeconds)
        }
        put("WrongEmailWithEndOfEmailReachedListener") {
            WrongEmailWithEndOfEmailReachedListener(description) {
                Toast.makeText(it, "End of email reached", Toast.LENGTH_SHORT).show()
            }
        }


        put("WronglyShowElapsedSecondsInSnackbarAfterDelay") {
            val elapsedSeconds = viewModel.elapsedSeconds.collectAsStateWithLifecycle().value
            WronglyShowElapsedSecondsInSnackbarAfterDelay(elapsedSeconds)
        }
        put("CorrectlyShowElapsedSecondsInSnackbarAfterDelay") {
            val elapsedSeconds = viewModel.elapsedSeconds.collectAsStateWithLifecycle().value
            CorrectlyShowElapsedSecondsInSnackbarAfterDelay(elapsedSeconds)
        }


        put("CorrectlyProduceStateWithContinuousEurRate") {
            val elapsedSeconds = viewModel.elapsedSeconds.collectAsStateWithLifecycle().value
            CorrectlyProduceStateWithContinuousEurRate(viewModel, elapsedSeconds)
        }
        put("WrongProduceStateWithContinuousEurRateUsingLaunchedEffect") {
            val elapsedSeconds = viewModel.elapsedSeconds.collectAsStateWithLifecycle().value
            WronglyProduceStateWithContinuousEurRateUsingLaunchedEffect(viewModel, elapsedSeconds)
        }


        put("WronglyContentWithMoveToTop") {
            WronglyContentWithMoveToTop(description)
        }
        put("CorrectlyContentWithMoveToTop") {
            CorrectlyContentWithMoveToTop(description)
        }
    }.toMap()
