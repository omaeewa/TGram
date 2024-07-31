package com.miracle.data.model

import org.drinkless.tdlib.TdApi

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val usernames: TdApi.Usernames?,
    val phoneNumber: String,
    val status: TdApi.UserStatus,
    val profilePhoto: ProfilePhoto?,
    val accentColorId: Int,
    val backgroundCustomEmojiId: Long,
    val profileAccentColorId: Int,
    val profileBackgroundCustomEmojiId: Long,
    val emojiStatus: TdApi.EmojiStatus?,
    val isContact: Boolean,
    val isMutualContact: Boolean,
    val isCloseFriend: Boolean,
    val isVerified: Boolean,
    val isPremium: Boolean,
    val isSupport: Boolean,
    val restrictionReason: String,
    val isScam: Boolean,
    val isFake: Boolean,
    val hasActiveStories: Boolean,
    val hasUnreadActiveStories: Boolean,
    val restrictsNewChats: Boolean,
    val haveAccess: Boolean,
    val type: TdApi.UserType,
    val languageCode: String,
    val addedToAttachmentMenu: Boolean
)

fun TdApi.User.toUser() = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    usernames = usernames,
    phoneNumber = phoneNumber,
    status = status,
    profilePhoto = profilePhoto?.toProfilePhoto(),
    accentColorId = accentColorId,
    backgroundCustomEmojiId = backgroundCustomEmojiId,
    profileAccentColorId = profileAccentColorId,
    profileBackgroundCustomEmojiId = profileBackgroundCustomEmojiId,
    emojiStatus = emojiStatus,
    isContact = isContact,
    isMutualContact = isMutualContact,
    isCloseFriend = isCloseFriend,
    isVerified = isVerified,
    isPremium = isPremium,
    isSupport = isSupport,
    restrictionReason = restrictionReason,
    isScam = isScam,
    isFake = isFake,
    hasActiveStories = hasActiveStories,
    hasUnreadActiveStories = hasUnreadActiveStories,
    restrictsNewChats = restrictsNewChats,
    haveAccess = haveAccess,
    type = type,
    languageCode = languageCode,
    addedToAttachmentMenu = addedToAttachmentMenu
)

data class ProfilePhoto(
    val id: Long,
    val small: TdApi.File,
    val big: TdApi.File,
    val minithumbnail: TdApi.Minithumbnail?,
    val hasAnimation: Boolean,
    val isPersonal: Boolean
)

fun TdApi.ProfilePhoto.toProfilePhoto() = ProfilePhoto(
    id = id,
    small = small,
    big = big,
    minithumbnail = minithumbnail,
    hasAnimation = hasAnimation,
    isPersonal = isPersonal
)
