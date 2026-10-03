package com.tunez.lifetree.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tunez.lifetree.di.AppModule
import com.tunez.lifetree.ui.navigation.LifeTreeNavigation
import com.tunez.lifetree.viewmodel.auth.SessionState
import com.tunez.lifetree.viewmodel.auth.SessionViewModel

@Composable
fun LifeTreeApp() {

    val context = LocalContext.current

    val sessionViewModel: SessionViewModel = viewModel(
        factory = AppModule.provideSessionViewModelFactory(context)
    )

    val sessionState by sessionViewModel
        .sessionState
        .collectAsStateWithLifecycle()

    when (sessionState) {

        SessionState.CHECKING -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        SessionState.AUTHENTICATED -> {
            LifeTreeNavigation(
                startDestination = "dashboard"
            )
        }

        SessionState.UNAUTHENTICATED -> {
            LifeTreeNavigation(
                startDestination = "welcome"
            )
        }
    }
}