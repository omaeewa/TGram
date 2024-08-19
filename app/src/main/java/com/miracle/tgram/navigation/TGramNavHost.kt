package com.miracle.tgram.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScreenTransition
import cafe.adriel.voyager.transitions.SlideTransition

@Composable
fun TGramNavHost(
    modifier: Modifier = Modifier,
    startDestination: Screen,
) {

    Box(modifier) {
        if (startDestination !is PlaceholderScreen)
            Navigator(screen = startDestination) { navigator ->
                BackStackParallax(navigator = navigator)
            }
    }
}