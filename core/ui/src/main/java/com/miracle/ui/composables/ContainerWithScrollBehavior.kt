package com.miracle.ui.composables

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import kotlin.math.max
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContainerWithScrollBehavior(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Sets the app bar's height offset to collapse the entire bar's height when content is
    // scrolled.
    var containerHeight by remember { mutableIntStateOf(0) }
    val heightOffsetLimit = -containerHeight.toFloat()
    SideEffect {
        if (scrollBehavior.state.heightOffsetLimit != heightOffsetLimit) {
            scrollBehavior.state.heightOffsetLimit = heightOffsetLimit
        }
    }

    // Set up support for resizing the top app bar when vertically dragging the bar itself.
    val appBarDragModifier = if (!scrollBehavior.isPinned) {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scrollBehavior.state.heightOffset += delta
            }
        )
    } else {
        Modifier
    }

    Surface(
        modifier = Modifier.then(appBarDragModifier),
//        color = SequoiaTheme.colorScheme.background
    ) {

        Layout(
            modifier = modifier,
            content = content
        ) { measurables, constraints ->
            val heightOffset = scrollBehavior.state.heightOffset.roundToInt()

            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            val placablesHeight = placeables.sumOf { it.height }
            if (containerHeight < placablesHeight)
                containerHeight = placablesHeight
            val height = max(0, placablesHeight + heightOffset)

            layout(constraints.maxWidth, height) {
                placeables.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = heightOffset)
                }
            }
        }
    }
}