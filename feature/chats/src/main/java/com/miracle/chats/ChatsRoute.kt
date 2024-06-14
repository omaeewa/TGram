package com.miracle.chats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChatsRoute(
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val userData by viewModel.userData.collectAsState()

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text("Hello ${userData.firstName}", color = Color.White, fontSize = 30.sp)
            Text("Your number ${userData.phoneNumber}", color = Color.White, fontSize = 30.sp)
        }
    }
}