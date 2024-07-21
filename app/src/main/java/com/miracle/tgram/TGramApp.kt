package com.miracle.tgram

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.miracle.tgram.navigation.TGramNavHost

@Composable
fun TGramApp(
    modifier: Modifier = Modifier,
    startDestination: Screen,
) {

    TGramNavHost(startDestination = startDestination, modifier = modifier)
}