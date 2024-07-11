package com.miracle.tgram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.miracle.authorization.navigation.Authorization
import com.miracle.chats.navigation.Chats
import com.miracle.tgram.navigation.Empty
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.mColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            splashScreen.setKeepOnScreenCondition { uiState is MainActivityUiState.Loading }

            val startDestination: Any = when (val state = uiState) {
                MainActivityUiState.Loading -> Empty
                is MainActivityUiState.Success -> {
                    if (state.isAuthorized) Chats else Authorization
                }
            }

            TGramTheme {
                TGramApp(
                    startDestination = startDestination,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(mColors.surface)
                )
            }
        }
    }
}
