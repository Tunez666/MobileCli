package com.tunez.lifetree.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tunez.lifetree.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())

    val uiState: StateFlow<RegisterUiState> =
        _uiState.asStateFlow()

    fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {

        if (username.isBlank()) {
            _uiState.value = RegisterUiState(
                error = "Введите имя пользователя"
            )
            return
        }

        if (email.isBlank()) {
            _uiState.value = RegisterUiState(
                error = "Введите email"
            )
            return
        }

        if (password.length < 6) {
            _uiState.value = RegisterUiState(
                error = "Пароль должен содержать минимум 6 символов"
            )
            return
        }

        if (password != confirmPassword) {
            _uiState.value = RegisterUiState(
                error = "Пароли не совпадают"
            )
            return
        }

        viewModelScope.launch {

            _uiState.value = RegisterUiState(
                isLoading = true
            )

            try {

                // Создаём пользователя
                authRepository.register(
                    username = username,
                    email = email,
                    password = password
                )

                // Сразу выполняем вход.
                // AuthRepository.login() сохранит JWT.
                authRepository.login(
                    email = email,
                    password = password
                )

                _uiState.value = RegisterUiState(
                    isSuccess = true
                )

            } catch (e: Exception) {

                _uiState.value = RegisterUiState(
                    error = e.message
                        ?: "Не удалось зарегистрироваться"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            error = null
        )
    }
}