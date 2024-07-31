package com.miracle.data.repository

import com.miracle.data.model.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    val me: Flow<User>
}