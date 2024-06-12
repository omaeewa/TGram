package com.miracle.tgram.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.miracle.authorization.navigation.Authorization
import com.miracle.authorization.navigation.authorizationScreen
import com.miracle.chats.navigation.chatsScreen
import com.miracle.chats.navigation.navigateToChats

@Composable
fun TGramNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = Authorization, modifier = modifier) {
        authorizationScreen(navigateToChats = navController::navigateToChats)
        chatsScreen()
    }
}