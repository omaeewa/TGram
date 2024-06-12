package com.miracle.authorization

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AuthorizationRoute(navigateToChats: () -> Unit) {
    Column {
        Text("authorization")

        Button(onClick = navigateToChats) {
            Text(text = "nav to chats")
        }
    }
}