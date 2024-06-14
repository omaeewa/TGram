package com.miracle.tgram.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.miracle.authorization.navigation.Authorization
import com.miracle.authorization.navigation.authorizationScreen
import com.miracle.chats.navigation.chatsScreen
import com.miracle.chats.navigation.navigateToChats

@SuppressLint("RestrictedApi")
@Composable
fun TGramNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        navController = navController,
        startDestination = Authorization,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        modifier = modifier
    ) {
        authorizationScreen(navigateToChats = {
            navController.navigateToChats {
                popUpTo(Authorization) {
                    inclusive = true
                }
            }
        })
        chatsScreen()
    }
}