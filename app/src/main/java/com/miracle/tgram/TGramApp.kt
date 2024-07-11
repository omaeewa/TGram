package com.miracle.tgram

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.miracle.tgram.navigation.Empty
import com.miracle.tgram.navigation.TGramNavHost

@Composable
fun TGramApp(
    modifier: Modifier = Modifier,
    startDestination: Any = Empty
) {

    TGramNavHost(startDestination = startDestination, modifier = modifier)
}