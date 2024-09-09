package com.miracle.chat

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.miracle.data.model.MessageText
import com.miracle.testing.repository.ChatRepositoryTest
import com.miracle.testing.repository.ChatsRepositoryTest
import com.miracle.ui.theme.TGramTheme
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.drinkless.tdlib.TdApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ChatRouteKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var viewModel: ChatViewModel
    private val currentChatId = 0L
    private val loadMessagesLimit = 20

    @Before
    fun setUp() = runTest {
        val chatsRepository = ChatsRepositoryTest().apply {
            loadMore(20)
            this.updateChat(currentChatId) {
                it.copy(permissions = TdApi.ChatPermissions().apply {
                    canSendBasicMessages = true
                })
            }
        }

        val chatRepository = ChatRepositoryTest(loadMessagesLimit, chatsRepository)
        viewModel = ChatViewModel(
            chatId = currentChatId,
            chatRepository = chatRepository
        )
    }

    @Test
    fun testInputText() {
        composeTestRule.setContent {
            TGramTheme {
                ChatRoute(onBackBtnClick = { }, viewModel = viewModel, navigateToChatInfo = {})
            }
        }

        val sendIconNode = composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.chat_screen_send_icon_test_tag))
        val bottomAppBarNode = composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.chat_screen_bottom_app_bar_test_tag))

        assertEquals("", viewModel.draftMessage.value)
        sendIconNode.assertDoesNotExist()

        val testMessage  = "hello"
        bottomAppBarNode.performTextInput(testMessage)
        assertEquals(testMessage, viewModel.draftMessage.value)

        sendIconNode.assertExists()
        sendIconNode.performClick()

        val newMessageSent = viewModel.messages.value.any { (it.messageContent as? MessageText)?.text?.text == testMessage }
        assert(newMessageSent)

        assertEquals("", viewModel.draftMessage.value)
        sendIconNode.assertDoesNotExist()
    }


}