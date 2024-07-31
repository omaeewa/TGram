package com.miracle.chats.model

import com.miracle.data.model.User


data class AccountItem(
    val userId: Long,
    val imageModel: Any?,
    val placeHolderRes: Int? = null,
    val title: String,
    val isCurrent: Boolean = false,
    val phoneNumber: String,
) {
    companion object {
        val dummy = AccountItem(
            userId = 0,
            imageModel = com.miracle.common.R.drawable.titarenko,
            placeHolderRes = com.miracle.common.R.drawable.titarenko,
            title = "Anastasia Titarenko",
            phoneNumber = "+480 (63) 287 12 43"
        )

        val empty = AccountItem(
            userId = 0,
            imageModel = null,
            title = "",
            phoneNumber = ""
        )
    }
}

fun User.toAccountItem() = AccountItem(
    userId = id,
    imageModel = profilePhoto?.small?.local?.path.takeIf { it?.isNotEmpty() == true }
        ?: profilePhoto?.minithumbnail?.data,
    title = "$firstName $lastName",
    isCurrent = true,
    phoneNumber = phoneNumber
)
