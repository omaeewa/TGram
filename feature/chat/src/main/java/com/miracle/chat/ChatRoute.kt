package com.miracle.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.miracle.chat.composables.ChatBottomAppBar
import com.miracle.chat.composables.ChatTopAppBar
import com.miracle.chat.composables.MessageTextContent
import com.miracle.chat.model.ChatInfo
import com.miracle.chat.model.MessageUi
import com.miracle.chat.model.MessageSendStatus
import com.miracle.chat.navigation.Chat
import com.miracle.data.model.FormattedText
import com.miracle.data.model.MessageDocument
import com.miracle.data.model.MessagePhoto
import com.miracle.data.model.MessageText
import com.miracle.data.model.MessageUnsupported
import com.miracle.data.model.MessageVideo
import com.miracle.ui.composables.MessageShape
import com.miracle.ui.composables.MessageType
import com.miracle.ui.composables.Side
import com.miracle.ui.composables.dummyGradientColors
import com.miracle.ui.composables.getBottomPadding
import com.miracle.ui.composables.getMessageType
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.lColors
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatRoute(
    chatData: Chat,
    onBackBtnClick: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val chat by viewModel.chatInfo.collectAsState()
    val messages by viewModel.messages.collectAsState()
    val inputMessageContent by viewModel.draftMessage.collectAsState()

    ChatScreen(
        chatInfo = chat,
        messageUis = messages,
        onBackBtnClick = onBackBtnClick,
        inputMessageContent = inputMessageContent,
        onInputMessageContentChange = viewModel::onDraftMessageChange,
        onSendMessageClick = viewModel::sendMessage,
        modifier = Modifier.fillMaxSize(),
        loadMoreMessages = viewModel::loadMoreMessages
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatInfo: ChatInfo,
    messageUis: List<MessageUi>,
    modifier: Modifier = Modifier,
    onSendMessageClick: () -> Unit = {},
    inputMessageContent: String = "",
    onInputMessageContentChange: (String) -> Unit = {},
    onBackBtnClick: () -> Unit = {},
    loadMoreMessages: () -> Unit = {}
) {
    val hazeState = remember { HazeState() }

    val gradientColors = dummyGradientColors
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val lazyColumnState = rememberLazyListState()
    val sdf = remember {
        SimpleDateFormat("MMMM dd", Locale.getDefault())
    }

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
            if (chatInfo.canSendMessages)
                ChatBottomAppBar(
                    modifier = Modifier
                        .hazeChild(state = hazeState)
                        .imePadding()
                        .windowInsetsPadding(BottomAppBarDefaults.windowInsets),
                    inputTextValue = inputMessageContent,
                    onInputTextValueChange = onInputMessageContentChange,
                    onSendMessageClick = onSendMessageClick
                )
            else {
                Surface {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .clickable {  }
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "MUTE",
                            color = mColors.primary,
                            style = mTypography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
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
            reverseLayout = true,
            state = lazyColumnState,
            contentPadding = PaddingValues(
                horizontal = lSpacing.tiny,
                vertical = lSpacing.small
            )
        ) {
            item {
                Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding() - lSpacing.small))
            }

            messageUis.groupBy { message ->
                val date = Date(message.date.toLong() * 1000)
                sdf.format(date)
            }.forEach { (date, groupMessages) ->

                itemsIndexed(groupMessages, key = { _, item -> item.id }) { index, message ->
                    val indexInList by remember(messageUis) {
                        derivedStateOf {
                            messageUis.indexOfFirst { it.id == message.id }
                        }
                    }

                    val messageType by remember(groupMessages) {
                        derivedStateOf {
                            getMessageType(messageUis = groupMessages, currentIndex = index)
                        }
                    }

                    val sendStatus = when {
                        message.id <= chatInfo.lastReadOutboxMessageId -> MessageSendStatus.SENT_READ
                        else -> MessageSendStatus.SENT_UNREAD
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
                            sendStatus = sendStatus
                        )
                    }

                    LaunchedEffect(key1 = Unit) {
                        if (indexInList == messageUis.lastIndex - 30 && messageUis.lastIndex >= 30)
                            loadMoreMessages()
                    }
                }

                //group header
                item {
                    DateItem(formattedDate = date)
                }

            }

            item {
                Spacer(modifier = Modifier.height(paddingValues.calculateTopPadding()))
            }
        }
    }
}

@Composable
private fun DateItem(modifier: Modifier = Modifier, formattedDate: String) {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(
            modifier
                .padding(bottom = lSpacing.small)
                .background(
                    lColors.chatDateBackground.copy(alpha = 0.7f),
                    RoundedCornerShape(50)
                )
        ) {
            Text(
                text = formattedDate,
                style = mTypography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = mColors.onSurface,
                modifier = Modifier.padding(
                    horizontal = lSpacing.small,
                    vertical = lSpacing.tiny
                )
            )
        }
    }
}


fun getMessageType(messageUis: List<MessageUi>, currentIndex: Int): MessageType {
    val message = messageUis[currentIndex]
    val prevMessage = messageUis.getOrNull(currentIndex + 1)
    val nextMessage = messageUis.getOrNull(currentIndex - 1)
    val currentMessageSide = if (message.isOutgoing) Side.Right else Side.Left

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
    message: MessageUi,
    sendStatus: MessageSendStatus,
    messageType: MessageType,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier,
) {
    MessageShape(
        modifier = modifier,
        gradientColors = gradientColors,
        messageType = messageType,
    ) {

        when (val content = message.messageContent) {
            is MessageText -> MessageTextContent(
                date = message.date,
                content = content,
                messageType = messageType,
                sendStatus = sendStatus
            )

            is MessagePhoto -> MessageTextContent(
                date = message.date,
                content = MessageText(content.caption.copy(text = "Photo ${content.caption.text}")),
                messageType = messageType,
                sendStatus = sendStatus
            )

            is MessageVideo -> MessageTextContent(
                date = message.date,
                content = MessageText(content.caption.copy(text = "Video ${content.caption.text}")),
                messageType = messageType,
                sendStatus = sendStatus
            )

            is MessageDocument -> MessageTextContent(
                date = message.date,
                content = MessageText(content.caption.copy(text = "Document ${content.caption.text}")),
                messageType = messageType,
                sendStatus = sendStatus
            )

            else -> MessageTextContent(
                date = message.date,
                content = MessageText(FormattedText("Unsupported Message")),
                messageType = messageType,
                sendStatus = sendStatus
            )
        }

    }
}


@Preview
@Composable
private fun MessageItemPreview() {
    TGramTheme {
        MessageItem(
            message = MessageUi.dummy.copy(messageContent = MessageText(text = FormattedText("Hello world"))),
            gradientColors = dummyGradientColors,
            messageType = MessageType.Single(Side.Left),
            sendStatus = MessageSendStatus.SENT_READ
        )
    }
}


@Preview
@Composable
private fun ChatScreenPreview() {
    val currentUserId = 12L
    val dummyMessageUis = (0..30L).map {
        val userId = listOf(currentUserId, 0).random()
        MessageUi.dummy.copy(id = it, userId = userId)
    }

    TGramTheme {
        ChatScreen(
            chatInfo = ChatInfo.dummy.copy(currentUserId = currentUserId),
            messageUis = dummyMessageUis
        )
    }
}



