package com.miracle.data.model

import org.drinkless.tdlib.TdApi

sealed interface MessageContent

fun TdApi.MessageContent.toMessageContent(): MessageContent = when (this) {
    is TdApi.MessageText -> toMessageText()
    is TdApi.MessagePhoto -> toMessagePhoto()
    is TdApi.MessageVideo -> toMessageVideo()
    is TdApi.MessageDocument -> toMessageDocument()
    else -> MessageUnsupported
}

data class MessageText(
    val text: FormattedText = FormattedText(),
    val webPage: TdApi.WebPage? = null,
    val linkPreviewOptions: TdApi.LinkPreviewOptions? = null
) : MessageContent

fun TdApi.MessageText.toMessageText() = MessageText(
    text = text.toFormattedText(),
    webPage = webPage,
    linkPreviewOptions = linkPreviewOptions
)

data class FormattedText(
    val text: String = "",
    val entities: List<TdApi.TextEntity> = emptyList()
)

fun TdApi.FormattedText.toFormattedText() = FormattedText(
    text = text,
    entities = entities.toList()
)


data class MessagePhoto(
    val photo: TdApi.Photo,
    val caption: FormattedText = FormattedText(),
    val showCaptionAboveMedia: Boolean = false,
    val hasSpoiler: Boolean = false,
    val isSecret: Boolean = false
) : MessageContent

fun TdApi.MessagePhoto.toMessagePhoto() = MessagePhoto(
    photo = photo,
    caption = caption.toFormattedText(),
    showCaptionAboveMedia = showCaptionAboveMedia,
    hasSpoiler = hasSpoiler,
    isSecret = isSecret
)

data object MessageUnsupported : MessageContent


data class MessageDocument(
    val document: TdApi.Document,
    val caption: FormattedText
) : MessageContent

fun TdApi.MessageDocument.toMessageDocument() = MessageDocument(
    document = document,
    caption = caption.toFormattedText()
)

data class MessageVideo(
    val video: TdApi.Video,
    val caption: FormattedText,
    val showCaptionAboveMedia: Boolean,
    val hasSpoiler: Boolean,
    val isSecret: Boolean
) : MessageContent

fun TdApi.MessageVideo.toMessageVideo() = MessageVideo(
    video = video,
    caption = caption.toFormattedText(),
    showCaptionAboveMedia = showCaptionAboveMedia,
    hasSpoiler = hasSpoiler,
    isSecret = isSecret
)
