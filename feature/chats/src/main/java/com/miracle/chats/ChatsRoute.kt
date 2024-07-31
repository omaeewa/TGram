package com.miracle.chats

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import com.miracle.chats.model.AccountItem
import kotlinx.coroutines.launch

@Composable
fun ChatsRoute(
    navigateToChat: (chatId: Long) -> Unit,
    viewModel: ChatsViewModel = hiltViewModel()
) {

    val chats by viewModel.chats.collectAsState()
    val currentAccount by viewModel.currentAccount.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.updateFirstScreenLoaded()
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RectangleShape,
                modifier = Modifier.widthIn(max = screenWidth.times((0.8).dp))
            ) {
                val accounts = (0 until 2).map { AccountItem.dummy }
                DrawerContent(
                    accounts = listOf(currentAccount) + accounts,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    ) {
        ChatsScreen(
            chats = chats,
            navigateToChat = navigateToChat,
            modifier = Modifier.fillMaxSize(),
            changeDrawerState = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }
        )
    }
}