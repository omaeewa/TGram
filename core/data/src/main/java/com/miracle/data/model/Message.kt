package com.miracle.data.model

import org.drinkless.tdlib.TdApi

data class Message(
    val id: Long,
    val senderId: TdApi.MessageSender,
    val chatId: Long,
    val sendingState: TdApi.MessageSendingState?,
    val schedulingState: TdApi.MessageSchedulingState?,
    val isOutgoing: Boolean,
    val isPinned: Boolean,
    val isFromOffline: Boolean,
    val canBeEdited: Boolean,
    val canBeForwarded: Boolean,
    val canBeRepliedInAnotherChat: Boolean,
    val canBeSaved: Boolean,
    val canBeDeletedOnlyForSelf: Boolean,
    val canBeDeletedForAllUsers: Boolean,
    val canGetAddedReactions: Boolean,
    val canGetStatistics: Boolean,
    val canGetMessageThread: Boolean,
    val canGetReadDate: Boolean,
    val canGetViewers: Boolean,
    val canGetMediaTimestampLinks: Boolean,
    val canReportReactions: Boolean,
    val hasTimestampedMedia: Boolean,
    val isChannelPost: Boolean,
    val isTopicMessage: Boolean,
    val containsUnreadMention: Boolean,
    val date: Int,
    val editDate: Int,
    val forwardInfo: TdApi.MessageForwardInfo?,
    val importInfo: TdApi.MessageImportInfo?,
    val interactionInfo: TdApi.MessageInteractionInfo?,
    val unreadReactions: List<TdApi.UnreadReaction>,
    val factCheck: TdApi.FactCheck?,
    val replyTo: TdApi.MessageReplyTo?,
    val messageThreadId: Long,
    val savedMessagesTopicId: Long,
    val selfDestructType: TdApi.MessageSelfDestructType?,
    val selfDestructIn: Double,
    val autoDeleteIn: Double,
    val viaBotUserId: Long,
    val senderBusinessBotUserId: Long,
    val senderBoostCount: Int,
    val authorSignature: String,
    val mediaAlbumId: Long,
    val effectId: Long,
    val restrictionReason: String,
    val content: MessageContent,
    val replyMarkup: TdApi.ReplyMarkup?
)

fun TdApi.Message.toMessage() = Message(
    id = id,
    senderId = senderId,
    chatId = chatId,
    sendingState = sendingState,
    schedulingState = schedulingState,
    isOutgoing = isOutgoing,
    isPinned = isPinned,
    isFromOffline = isFromOffline,
    canBeEdited = canBeEdited,
    canBeForwarded = canBeForwarded,
    canBeRepliedInAnotherChat = canBeRepliedInAnotherChat,
    canBeSaved = canBeSaved,
    canBeDeletedOnlyForSelf = canBeDeletedOnlyForSelf,
    canBeDeletedForAllUsers = canBeDeletedForAllUsers,
    canGetAddedReactions = canGetAddedReactions,
    canGetStatistics = canGetStatistics,
    canGetMessageThread = canGetMessageThread,
    canGetReadDate = canGetReadDate,
    canGetViewers = canGetViewers,
    canGetMediaTimestampLinks = canGetMediaTimestampLinks,
    canReportReactions = canReportReactions,
    hasTimestampedMedia = hasTimestampedMedia,
    isChannelPost = isChannelPost,
    isTopicMessage = isTopicMessage,
    containsUnreadMention = containsUnreadMention,
    date = date,
    editDate = editDate,
    forwardInfo = forwardInfo,
    importInfo = importInfo,
    interactionInfo = interactionInfo,
    unreadReactions = unreadReactions.toList(),
    factCheck = factCheck,
    replyTo = replyTo,
    messageThreadId = messageThreadId,
    savedMessagesTopicId = savedMessagesTopicId,
    selfDestructType = selfDestructType,
    selfDestructIn = selfDestructIn,
    autoDeleteIn = autoDeleteIn,
    viaBotUserId = viaBotUserId,
    senderBusinessBotUserId = senderBusinessBotUserId,
    senderBoostCount = senderBoostCount,
    authorSignature = authorSignature,
    mediaAlbumId = mediaAlbumId,
    effectId = effectId,
    restrictionReason = restrictionReason,
    content = content.toMessageContent(),
    replyMarkup = replyMarkup
)
