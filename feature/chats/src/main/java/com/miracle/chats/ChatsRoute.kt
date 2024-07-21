package com.miracle.chats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChatsRoute(
    navigateToChat: (chatId: Long) -> Unit,
    viewModel: ChatsViewModel = hiltViewModel()
) {

    val chats by viewModel.chats.collectAsState()


    LaunchedEffect(key1 = Unit) {
        viewModel.updateFirstScreenLoaded()
    }

    ChatsScreen(chats = chats, navigateToChat = navigateToChat)
}