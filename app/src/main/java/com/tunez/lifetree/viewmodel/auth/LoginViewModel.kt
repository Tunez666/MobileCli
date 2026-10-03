package com.tunez.lifetree.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tunez.lifetree.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(
        email: String,
        password: String
    ) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState(
                error = "Заполни email и пароль"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState(
                isLoading = true
            )

            try {
                authRepository.login(
                    email = email,
                    password = password
                )

                _uiState.value = LoginUiState(
                    isSuccess = true
                )
            } catch (e: Exception) {
                _uiState.value = LoginUiState(
                    error = e.message ?: "Не удалось выполнить вход"
                )
            }
        }
    }
}