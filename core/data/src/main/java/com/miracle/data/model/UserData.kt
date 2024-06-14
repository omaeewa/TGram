package com.miracle.data.model

import org.drinkless.td.libcore.telegram.TdApi

data class UserData(
    val id: Long = 0,
    val firstName: String = "",
    val phoneNumber: String = ""
)


fun TdApi.User.toUserData() = UserData(id = id, firstName = firstName, phoneNumber = phoneNumber)