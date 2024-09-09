package com.miracle.chat

import com.miracle.data.model.FormattedText
import com.miracle.data.model.MessageText
import com.miracle.testing.repository.ChatRepositoryTest
import com.miracle.testing.repository.ChatsRepositoryTest
import com.miracle.testing.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChatViewModelTest {

    private lateinit var viewModel: ChatViewModel
    private val currentChatId = 0L
    private val loadMessagesLimit = 20

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() = runTest {
        val chatsRepository = ChatsRepositoryTest().apply {
            loadMore(20)
        }

        val chatRepository = ChatRepositoryTest(loadMessagesLimit, chatsRepository)
        viewModel = ChatViewModel(
            chatId = currentChatId,
            chatRepository = chatRepository
        )
    }

    @Test
    fun `check on draft message change`() = runTest {
        assertEquals(viewModel.draftMessage.value, "")

        listOf("aboba", "ab", "").forEach { testMessage ->
            viewModel.onDraftMessageChange(testMessage)
            assertEquals(viewModel.draftMessage.value, testMessage)
        }
    }


    @Test
    fun `check send message`() {
        var messagesSize = viewModel.messages.value.size


        listOf("message1", "aboba", "hello world").forEach { messageText ->
            viewModel.onDraftMessageChange(messageText)
            assertEquals(viewModel.draftMessage.value, messageText)

            viewModel.sendMessage()
            assertEquals(viewModel.draftMessage.value, "")

            assert(viewModel.messages.value.any {
                it.id == messagesSize.toLong() &&
                        it.messageContent == MessageText(text = FormattedText(text = messageText))
            })
            messagesSize++
        }
    }

    @Test
    fun `check load more messages`() {

        repeat(5) {
            val prevSize = viewModel.messages.value.size
            viewModel.loadMoreMessages()
            val currentSize = viewModel.messages.value.size
            assertEquals(currentSize, loadMessagesLimit + prevSize)
        }
    }

}