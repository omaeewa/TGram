package com.miracle.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.miracle.chat.model.ChatInfo
import com.miracle.chat.model.Message
import com.miracle.chat.navigation.Chat
import com.miracle.ui.composables.MessageShape
import com.miracle.ui.composables.MessageType
import com.miracle.ui.composables.ProfilePhoto
import com.miracle.ui.composables.ProfilePhotoSize
import com.miracle.ui.composables.Side
import com.miracle.ui.composables.dummyGradientColors
import com.miracle.ui.composables.getBottomPadding
import com.miracle.ui.composables.getMessageType
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.flow.flowOf

@Composable
fun ChatRoute(
    chatData: Chat,
    onBackBtnClick: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val chat by viewModel.chatInfo.collectAsState()
    val messages = viewModel.messagesPager.collectAsLazyPagingItems()

    ChatScreen(
        chatInfo = chat,
        messages = messages,
        onBackBtnClick = onBackBtnClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatInfo: ChatInfo,
    messages: LazyPagingItems<Message>,
    modifier: Modifier = Modifier,
    onBackBtnClick: () -> Unit = {}
) {
    var inputValue by remember {
        mutableStateOf("")
    }
    val hazeState = remember { HazeState() }

    val gradientColors = dummyGradientColors
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp


    Scaffold(
        topBar = {
            ChatTopAppBar(
                chatInfo = chatInfo,
                modifier = Modifier.hazeChild(state = hazeState),
                onBackBtnClick = onBackBtnClick,
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            ChatBottomAppBar(
                modifier = Modifier
                    .hazeChild(state = hazeState)
                    .imePadding()
                    .windowInsetsPadding(BottomAppBarDefaults.windowInsets),
                inputTextValue = inputValue,
                onInputTextValueChange = { inputValue = it }
            )
        },
        containerColor = Color.Black,
        modifier = modifier
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .haze(
                    hazeState,
                    backgroundColor = mColors.surfaceContainer,
                    blurRadius = 30.dp,
                ),
            reverseLayout = true
        ) {
            item {
                Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
            }

            items(messages.itemCount, key = messages.itemKey { it.id }) {
                val message = messages[it]!!

                val messageType by remember {
                    derivedStateOf {
                        getMessageType(
                            messages = messages.itemSnapshotList.items,
                            currentIndex = it,
                            currentUserId = chatInfo.currentUserId
                        )
                    }
                }

                Box(Modifier.fillMaxWidth()) {

                    MessageItem(
                        message = message,
                        gradientColors = gradientColors,
                        modifier = Modifier
                            .widthIn(max = screenWidth * 0.8f)
                            .align(if (messageType.isRightSide()) Alignment.CenterEnd else Alignment.CenterStart)
                            .padding(bottom = messageType.getBottomPadding()),
                        messageType = messageType,
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(paddingValues.calculateTopPadding()))
            }
        }
    }
}


fun getMessageType(messages: List<Message>, currentIndex: Int, currentUserId: Long): MessageType {
    val message = messages[currentIndex]
    val prevMessage = messages.getOrNull(currentIndex + 1)
    val nextMessage = messages.getOrNull(currentIndex - 1)
    val currentMessageSide = if (message.userId == currentUserId) Side.Right else Side.Left

    return getMessageType(
        previousTimestamp = prevMessage?.date,
        currentTimestamp = message.date,
        nextTimestamp = nextMessage?.date,
        currentMessageSide = currentMessageSide,
        isPrevSameUser = prevMessage?.userId == message.userId,
        isNextSameUser = nextMessage?.userId == message.userId,
    )
}

@Composable
fun MessageItem(
    message: Message,
    messageType: MessageType,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier,
) {
    MessageShape(
        modifier = modifier,
        gradientColors = gradientColors,
        messageType = messageType,
    ) {

        MessageTextContent(message = message, messageType = messageType)
    }
}


@Preview
@Composable
private fun MessageItemPreview() {
    TGramTheme {
        MessageItem(
            message = Message.dummy.copy(message = "Для четкого различия функций форматирования временных меток и чтобы названия лучше в Для четкого различия функций форматированdff"),
            gradientColors = dummyGradientColors,
            messageType = MessageType.Single(Side.Left)
        )
    }
}


@Preview
@Composable
private fun ChatScreenPreview() {
    val currentUserId = 12L
    val dummyMessages = (0..30L).map {
        val userId = listOf(currentUserId, 0).random()
        Message.dummy.copy(id = it, userId = userId)
    }

    val messages = flowOf(PagingData.from(dummyMessages)).collectAsLazyPagingItems()
    TGramTheme {
        ChatScreen(
            chatInfo = ChatInfo.dummy.copy(currentUserId = currentUserId),
            messages = messages
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar(
    chatInfo: ChatInfo,
    modifier: Modifier = Modifier,
    onBackBtnClick: () -> Unit = {},
    onMoreBtnClick: () -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackBtnClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ProfilePhoto(
                    imageModel = chatInfo.imageModel,
                    title = chatInfo.title,
                    userId = chatInfo.id,
                    profilePhotoSize = ProfilePhotoSize.medium,
                    placeholderRes = chatInfo.placeholderRes
                )

                Spacer(modifier = Modifier.width(lSpacing.small))

                Column {
                    Text(
                        text = chatInfo.title,
                        style = mTypography.titleMedium,
                        color = mColors.onSurface
                    )
                    Text(
                        text = "last seen recently",
                        style = mTypography.bodyLarge,
                        color = mColors.secondary,
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onMoreBtnClick) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
            }
        },
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ChatTopAppBarPreview() {
    TGramTheme {
        ChatTopAppBar(chatInfo = ChatInfo.dummy)
    }
}

@Composable
fun ChatBottomAppBar(
    modifier: Modifier = Modifier,
    inputTextValue: String = "",
    onInputTextValueChange: (String) -> Unit = {},
    onStickerIconClick: () -> Unit = {},
    onAttachIconClick: () -> Unit = {},
    onVoiceIconClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onStickerIconClick) {
            Icon(
                painter = painterResource(id = R.drawable.msg2_sticker),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = mColors.secondary
            )
        }

        BasicTextField(
            value = inputTextValue,
            onValueChange = onInputTextValueChange,
            modifier = Modifier.weight(1f),
        )

        IconButton(onClick = onAttachIconClick) {
            Icon(
                painter = painterResource(id = R.drawable.msg_input_attach2),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = mColors.secondary
            )
        }

        IconButton(onClick = onVoiceIconClick) {
            Icon(
                painter = painterResource(id = R.drawable.msg_voice_unmuted),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = mColors.secondary
            )
        }
    }
}

@Preview
@Composable
private fun ChatBottomAppBarPreview() {
    TGramTheme {
        ChatBottomAppBar(Modifier.background(mColors.surfaceContainer))
    }
}