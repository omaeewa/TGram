package com.miracle.tgram.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun TGramNavHost(
    modifier: Modifier = Modifier,
    startDestination: Screen,
) {

    if (startDestination !is PlaceholderScreen)
        Navigator(screen = startDestination) { navigator ->
            BackStackParallax(navigator = navigator)
        }
}