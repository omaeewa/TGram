package com.miracle.tgram.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.miracle.authorization.navigation.Authorization
import com.miracle.authorization.navigation.authorizationScreen
import com.miracle.chat.navigation.chatScreen
import com.miracle.chat.navigation.navigateToChat
import com.miracle.chats.navigation.chatsScreen
import com.miracle.chats.navigation.navigateToChats

@Composable
fun TGramNavHost(
    modifier: Modifier = Modifier,
    startDestination: Any = Authorization,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            fadeIn() + slideInHorizontally(
                initialOffsetX = { it / 4 }
            )
        },
        exitTransition = {
            fadeOut() + slideOutHorizontally(
                targetOffsetX = { it / 4 }
            )
        },
        popEnterTransition = {
            fadeIn()
        },
        popExitTransition = {
            fadeOut()
        },
        modifier = modifier
    ) {
        authorizationScreen(navigateToChats = {
            navController.navigateToChats {
                popUpTo(Authorization) {
                    inclusive = true
                }
            }
        })
        chatsScreen(navigateToChat = navController::navigateToChat)
        chatScreen(onBackBtnClick = navController::popBackStack)
    }
}