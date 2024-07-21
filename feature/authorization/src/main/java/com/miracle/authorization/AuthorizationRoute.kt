package com.miracle.authorization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.miracle.data.model.AuthState

@Composable
fun AuthorizationRoute(
    navigateToChats: () -> Unit,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val authState by viewModel.authState.collectAsState()
    val code by viewModel.authCode.collectAsState()

    LaunchedEffect(authState) {
        if (authState == AuthState.Ready)
            navigateToChats()
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.updateFirstScreenLoaded()
    }

    var screenState by remember {
        mutableStateOf(AuthState.Unexpected)
    }

    LaunchedEffect(key1 = authState) {
        if (authState in listOf(AuthState.WaitPhoneNumber, AuthState.WaitCode))
            screenState = authState
    }

    when (screenState) {
        AuthState.WaitPhoneNumber -> InputPhoneNumberScreen(
            phoneNumber = phoneNumber,
            onPhoneNumberChange = viewModel::onPhoneNumberChange,
            setPhoneNumber = viewModel::setAuthPhoneNumber
        )

        AuthState.WaitCode -> InputCodeScreen(
            code = code,
            onCodeChange = viewModel::onAuthCodeChange,
            setCode = viewModel::setAuthCode,
        )

        else -> {}
    }
}
