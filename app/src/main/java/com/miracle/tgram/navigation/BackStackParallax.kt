package com.miracle.tgram.navigation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow

enum class DismissValue {
    Default,
    DismissedToEnd,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BackStackParallax(navigator: Navigator) {
    val density = LocalDensity.current

    val coroutineScope = rememberCoroutineScope()

    var currentScreen by remember { mutableStateOf<ScreenHolder?>(null) }
    val animatedScreens = remember { mutableStateListOf<ScreenHolder>() }
    var peekingScreen by remember { mutableStateOf<ScreenHolder?>(null) }



    BoxWithConstraints(Modifier.fillMaxSize()) {
        val maxWidthPx = constraints.maxWidth.toFloat()

        val anchors by remember(maxWidthPx) {
            derivedStateOf {
                DraggableAnchors {
                    DismissValue.Default at 0f
                    DismissValue.DismissedToEnd at maxWidthPx * 2
                }
            }
        }

        val anchoredDraggableState = remember {
            AnchoredDraggableState(
                initialValue = DismissValue.Default,
                anchors = anchors,
                positionalThreshold = { distance -> distance * 0.2f },
                velocityThreshold = { with(density) { 125.dp.toPx() } },
                snapAnimationSpec = SpringSpec(stiffness = StiffnessLow),
                decayAnimationSpec = exponentialDecay()
            )
        }

        LaunchedEffect(anchors) {
            anchoredDraggableState.updateAnchors(anchors)
        }

        val lastEvent = navigator.lastEvent

        val currentValue = anchoredDraggableState.currentValue
        val offset = anchoredDraggableState.offset


        LaunchedEffect(navigator.lastItemOrNull?.key) {
            if (navigator.lastItemOrNull != null) {
                // Remove all transitions when lastItem is changed
                animatedScreens.forEach { it.transition = null }

                val foundScreen = animatedScreens.findLast { it.screen == navigator.lastItem }
                val newScreen = foundScreen ?: ScreenHolder(navigator.lastItem)

                // Screen can already be in animatedScreens when peeking
                if (foundScreen == null) {
                    if (lastEvent == StackEvent.Pop) {
                        animatedScreens.add(0, newScreen)
                    } else {
                        animatedScreens.add(newScreen)
                    }
                }

                currentScreen?.let { currentScreen ->
                    if (currentValue == DismissValue.Default) {

                        if (currentScreen.transition == null) {
                            currentScreen.transition = SlideTransition()
                        }

                        if (newScreen.transition == null) {
                            newScreen.transition = SlideTransition()
                        }

                        coroutineScope.launch {
                            newScreen.transition?.startTransition(
                                lastStackEvent = lastEvent,
                                isAnimatingIn = true,
                            )

                            newScreen.transition = null
                        }

                        coroutineScope.launch {
                            currentScreen.transition?.startTransition(
                                lastStackEvent = lastEvent,
                                isAnimatingAway = true,
                            )

                            animatedScreens.remove(currentScreen)
                        }
                    } else {
                        animatedScreens.remove(currentScreen)
                    }
                }

                currentScreen = newScreen
                anchoredDraggableState.anchoredDrag { dragTo(0f) }
            }
        }

        LaunchedEffect(offset) {
            if (currentValue == DismissValue.Default) {
                if (offset > 0f) {
                    if (currentScreen?.transition == null && navigator.size >= 2) {
                        currentScreen?.transition = SlideTransition()
                        currentScreen?.transition?.startPeeking(isPrevScreen = false)

                        peekingScreen = ScreenHolder(navigator.items[navigator.size - 2])

                        peekingScreen?.let { peekingScreen ->
                            peekingScreen.transition = SlideTransition()
                            peekingScreen.transition?.startPeeking(isPrevScreen = true)

                            animatedScreens.add(0, peekingScreen)
                        }
                    }

                    peekingScreen?.let { peekingScreen ->
                        val peekingFraction = offset / maxWidthPx

                        coroutineScope.launch {
                            currentScreen?.transition?.transitionAnimatable?.snapTo(peekingFraction)
                        }

                        coroutineScope.launch {
                            peekingScreen.transition?.transitionAnimatable?.snapTo(peekingFraction)
                        }
                    }
                } else {

                    peekingScreen?.let { peekingScreen ->
                        currentScreen?.let { currentScreen ->
                            coroutineScope.launch {
                                currentScreen.transition?.stopPeeking()
                                currentScreen.transition = null
                            }
                        }

                        coroutineScope.launch {
                            peekingScreen.transition?.stopPeeking()
                            peekingScreen.transition = null
                            animatedScreens.remove(peekingScreen)
                        }
                    }

                    peekingScreen = null
                }
            }
        }

        LaunchedEffect(currentValue) {
            if (currentValue == DismissValue.DismissedToEnd) {
                peekingScreen = null

                if (navigator.canPop) {
                    navigator.pop()
                } else {
                    navigator.parent?.pop()
                }
            }
        }

        val currentScreenModifier = Modifier.anchoredDraggable(
            state = anchoredDraggableState,
            orientation = Orientation.Horizontal,
            enabled = navigator.canPop && currentValue == DismissValue.Default,
            reverseDirection = LocalLayoutDirection.current == LayoutDirection.Rtl,
        )

        animatedScreens.fastForEach { screen ->
            key(screen.screen.key) {
                navigator.saveableState("transition", screen.screen) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .animatingModifier(screen)
                            .then(if (screen == currentScreen) currentScreenModifier else Modifier)
                    ) {
                        screen.screen.Content()
                    }

                    //if dragging block screen interaction
                    if (offset > 0 && !anchoredDraggableState.isAnimationRunning)
                        Box(Modifier.fillMaxSize().pointerInput(Unit) {})
                }
            }
        }
    }
}

private fun Modifier.animatingModifier(screenHolder: ScreenHolder) =
    screenHolder.run { this@animatingModifier.animatingModifier() }

private class ScreenHolder(val screen: Screen) {
    var transition by mutableStateOf<NavigatorScreenTransition?>(null)

    fun Modifier.animatingModifier(): Modifier =
        transition?.run { this@animatingModifier.animatingModifier() } ?: this
}


abstract class NavigatorScreenTransition {
    var lastStackEvent by mutableStateOf(StackEvent.Idle)
    var isAnimatingIn by mutableStateOf(false)
    var isAnimatingAway by mutableStateOf(false)
    var transitionAnimatable = Animatable(0f)
    var isDragging by mutableStateOf(true)
    var easeFunc: (Float) -> Float = { (-0.5f * (cos(PI * it) - 1f)).toFloat() }

    fun startPeeking(isPrevScreen: Boolean) {
        this.lastStackEvent = StackEvent.Pop
        this.isAnimatingIn = isPrevScreen
        this.isAnimatingAway = !isPrevScreen
    }

    suspend fun stopPeeking() {
        val durationMillis = 250f * (1f - transitionAnimatable.value)

        transitionAnimatable.animateTo(0f, tween(durationMillis.toInt(), easing = LinearEasing))
    }

    suspend fun startTransition(
        lastStackEvent: StackEvent,
        isAnimatingIn: Boolean = false,
        isAnimatingAway: Boolean = false,
        isDragging: Boolean = false,
    ) {
        this.lastStackEvent = lastStackEvent
        this.isAnimatingIn = isAnimatingIn
        this.isAnimatingAway = isAnimatingAway
        this.isDragging = isDragging

        val (initial, target) = when {
            isAnimatingIn -> 0.6f to 1f
            isAnimatingAway -> 0f to 0.4f
            else -> 0f to 1f
        }

        transitionAnimatable = Animatable(initial)
        transitionAnimatable.animateTo(target, tween(150, easing = LinearEasing))
    }

    abstract fun Modifier.animatingModifier(): Modifier
}

fun scaleValue(start: Float, end: Float, currentValue: Float, exponent: Int = 1): Float {
    val linearValue = (currentValue - start) / (end - start)
    return linearValue.pow(exponent)
}

private class SlideTransition : NavigatorScreenTransition() {
    override fun Modifier.animatingModifier(): Modifier =
        composed {
            var modifier = this

            val isPop = lastStackEvent == StackEvent.Pop

            val transitionFractionState by remember(transitionAnimatable.value) {
                transitionAnimatable.asState()
            }

            val transitionFraction by remember(transitionFractionState) {
                derivedStateOf { easeFunc(transitionFractionState) }
            }

            if (isAnimatingAway) {
                modifier = if (isPop) {
                    val alphaFraction =
                        (1 - scaleValue(start = 0f, end = 0.4f, currentValue = transitionFraction)).pow(5)
                    val alpha = if (!isDragging) Modifier.alpha(alphaFraction) else Modifier

                    modifier
                        .then(alpha)
                        .slideFraction(transitionFraction)
                } else {
                    modifier
                        .background(MaterialTheme.colorScheme.background)
                        .slideFraction(-0.25f * transitionFraction)
                        .drawWithContent {
                            drawContent()
                            drawRect(Color.Black, alpha = transitionFraction * 0.25f)
                        }
                }
            } else if (isAnimatingIn) {
                modifier = if (isPop) {
                    modifier
                        .background(MaterialTheme.colorScheme.background)
                        .slideFraction(-0.25f + (0.25f * transitionFraction))
                        .drawWithContent {
                            drawContent()
                            drawRect(Color.Black, alpha = 0.25f - (transitionFraction * 0.25f))
                        }
                } else {
                    val alphaFraction =
                        scaleValue(start = 0.6f, end = 1f, currentValue = transitionFraction, exponent = 5)

                    val alpha = if (!isDragging) Modifier.alpha(alphaFraction) else Modifier

                    modifier
                        .then(alpha)
                        .slideFraction(1f - transitionFraction)
                }
            }

            modifier
        }

    private fun Modifier.slideFraction(fraction: Float): Modifier =
        this.layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)
            val measuredSize = IntSize(placeable.width, placeable.height)

            layout(placeable.width, placeable.height) {
                val slideValue = (measuredSize.width.toFloat() * fraction).toInt()

                placeable.placeWithLayer(IntOffset(x = slideValue, y = 0))
            }
        }
}