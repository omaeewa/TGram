package com.miracle.tgram.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miracle.authorization.navigation.Authorization
import com.miracle.authorization.navigation.authorizationScreen
import com.miracle.chat.navigation.chatScreen
import com.miracle.chat.navigation.navigateToChat
import com.miracle.chats.navigation.chatsScreen
import com.miracle.chats.navigation.navigateToChats
import kotlinx.serialization.Serializable

@Composable
fun TGramNavHost(
    modifier: Modifier = Modifier,
    startDestination: Any = Empty,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it / 6 }
            ) + fadeIn()
        },
        exitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            fadeIn()
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it / 6 }
            ) + fadeOut()
        },
        modifier = modifier
    ) {
        emptyScreen()

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

fun NavGraphBuilder.emptyScreen() = composable<Empty>(
    enterTransition = { EnterTransition.None },
    exitTransition = { ExitTransition.None }
) { Box(modifier = Modifier.fillMaxSize()) }

@Serializable
object Empty