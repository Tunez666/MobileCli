package com.tunez.lifetree.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.tunez.lifetree.data.api.ApiClient
import com.tunez.lifetree.data.local.TokenStorage
import com.tunez.lifetree.data.repository.AuthRepository
import com.tunez.lifetree.viewmodel.auth.LoginViewModelFactory
import com.tunez.lifetree.viewmodel.auth.RegisterViewModelFactory
import com.tunez.lifetree.viewmodel.auth.SessionViewModelFactory

object AppModule {

    fun provideTokenStorage(
        context: Context
    ): TokenStorage {
        return TokenStorage(
            context.applicationContext
        )
    }

    fun provideAuthRepository(
        context: Context
    ): AuthRepository {

        val tokenStorage =
            provideTokenStorage(context)

        val authApi =
            ApiClient.createAuthApi(
                tokenStorage = tokenStorage
            )

        return AuthRepository(
            authApi = authApi,
            tokenStorage = tokenStorage
        )
    }

    fun provideRegisterViewModelFactory(
        context: Context
    ): RegisterViewModelFactory {

        return RegisterViewModelFactory(
            authRepository = provideAuthRepository(context)
        )
    }

    fun provideLoginViewModelFactory(
        context: Context
    ): LoginViewModelFactory {

        return LoginViewModelFactory(
            authRepository = provideAuthRepository(context)
        )
    }

    fun provideSessionViewModelFactory(
        context: Context
    ): SessionViewModelFactory {

        return SessionViewModelFactory(
            authRepository = provideAuthRepository(context)
        )
    }
}