package com.miracle.data.repository

import com.miracle.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userData: Flow<UserData>
}