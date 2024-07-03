package com.miracle.tgram

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.miracle.authorization.navigation.Authorization
import com.miracle.tgram.navigation.TGramNavHost

@Composable
fun TGramApp(
    modifier: Modifier = Modifier,
    startDestination: Any = Authorization
) {

    TGramNavHost(startDestination = startDestination, modifier = modifier)
}