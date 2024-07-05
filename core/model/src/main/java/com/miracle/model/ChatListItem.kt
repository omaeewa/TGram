package com.miracle.model

data class ChatListItem(
    val id: Long,
    val title: String,
    val imageModel: Any?,
    val placeholderRes: Int? = null,
    val isMuted: Boolean,
    val unreadCount: Int,
    val lastMessage: String?,
    val date: Int? //Timestamp
) {

    companion object {
        val empty = ChatListItem(
            id = 0,
            title = "",
            imageModel = null,
            isMuted = false,
            unreadCount = 0,
            lastMessage = null,
            date = null
        )
    }
}
