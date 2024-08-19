package com.miracle.chatinfo

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography
import kotlinx.coroutines.launch

@Composable
fun MediaTabRow(
    tabTypes: List<TabType>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val animationScope = rememberCoroutineScope()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedTabIndex = page
        }
    }


    TabRow(
        selectedTabIndex = selectedTabIndex,
        divider = {},
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                Box(
                    modifier
                        .customTabIndicatorOffset(
                            currentTabPosition = tabPositions[selectedTabIndex],
                            width = 50.dp
                        )
                        .height(3.dp)
                        .background(
                            color = mColors.primary,
                            RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp)
                        )

                )
            }
        }
    ) {
        tabTypes.forEachIndexed { index, i ->
            val tab = tabTypes[index]
            val selected = index == selectedTabIndex
            val textColor = when {
                selected -> mColors.primary
                else -> mColors.secondary
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .clickable {
                        selectedTabIndex = index
                        animationScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab.name,
                    modifier = Modifier
                        .padding(top = lSpacing.medium, bottom = 13.dp),
                    color = textColor,
                    style = mTypography.bodyLarge,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

private fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    width: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.width,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = max(0.dp, currentTabWidth - width) / 2 + indicatorOffset)
        .width(width)
}