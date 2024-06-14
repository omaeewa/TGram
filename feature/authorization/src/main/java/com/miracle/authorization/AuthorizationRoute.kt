package com.miracle.authorization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    when (authState) {
        AuthState.WaitPhoneNumber -> InputPhoneNumberScreen(
            phoneNumber = phoneNumber,
            onPhoneNumberChange = viewModel::onPhoneNumberChange,
            setPhoneNumber = viewModel::setAuthPhoneNumber
        )

        AuthState.WaitCode -> InputCodeScreen(
            code = code,
            onCodeChange = viewModel::onAuthCodeChange,
            setCode = viewModel::setAuthCode
        )

        else -> {}
    }
}
