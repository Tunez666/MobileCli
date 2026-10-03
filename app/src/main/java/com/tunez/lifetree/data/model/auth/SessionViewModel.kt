package com.tunez.lifetree.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tunez.lifetree.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class SessionState {
    CHECKING,
    AUTHENTICATED,
    UNAUTHENTICATED
}

class SessionViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _sessionState =
        MutableStateFlow(SessionState.CHECKING)

    val sessionState: StateFlow<SessionState> =
        _sessionState.asStateFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {

            if (!authRepository.hasToken()) {
                _sessionState.value =
                    SessionState.UNAUTHENTICATED

                return@launch
            }

            try {
                authRepository.getMe()

                _sessionState.value =
                    SessionState.AUTHENTICATED

            } catch (e: Exception) {

                authRepository.logout()

                _sessionState.value =
                    SessionState.UNAUTHENTICATED
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()

            _sessionState.value =
                SessionState.UNAUTHENTICATED
        }
    }
}