package com.miracle.chats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChatsRoute(
    viewModel: ChatsViewModel = hiltViewModel()
) {

    val chats by viewModel.chats.collectAsState()

    ChatsScreen(chats = chats)
}