package com.tunez.lifetree.ui.navigation

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tunez.lifetree.di.AppModule
import com.tunez.lifetree.ui.screens.auth.LoginScreen
import com.tunez.lifetree.ui.screens.auth.RegisterScreen
import com.tunez.lifetree.ui.screens.dashboard.DashboardScreen
import com.tunez.lifetree.ui.screens.welcome.WelcomeScreen
import com.tunez.lifetree.viewmodel.auth.SessionState
import com.tunez.lifetree.viewmodel.auth.SessionViewModel

object LifeTreeRoutes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val DASHBOARD = "dashboard"
}

@Composable
fun LifeTreeNavigation() {

    val navController = rememberNavController()
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
            NavHost(
                navController = navController,
                startDestination = LifeTreeRoutes.DASHBOARD
            ) {

                composable(LifeTreeRoutes.DASHBOARD) {
                    DashboardScreen()
                }

                composable(LifeTreeRoutes.LOGIN) {
                    LoginScreen(
                        viewModelFactory =
                            AppModule.provideLoginViewModelFactory(context),

                        onLoginSuccess = {
                            navController.navigate(
                                LifeTreeRoutes.DASHBOARD
                            ) {
                                popUpTo(
                                    LifeTreeRoutes.LOGIN
                                ) {
                                    inclusive = true
                                }
                            }
                        },

                        onRegister = {
                            navController.navigate(
                                LifeTreeRoutes.REGISTER
                            )
                        },

                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(LifeTreeRoutes.REGISTER) {
                    RegisterScreen(
                        viewModelFactory =
                            AppModule.provideRegisterViewModelFactory(context),

                        onRegisterSuccess = {
                            navController.navigate(
                                LifeTreeRoutes.DASHBOARD
                            ) {
                                popUpTo(
                                    LifeTreeRoutes.REGISTER
                                ) {
                                    inclusive = true
                                }
                            }
                        },

                        onLogin = {
                            navController.popBackStack()
                        },

                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }

        SessionState.UNAUTHENTICATED -> {
            NavHost(
                navController = navController,
                startDestination = LifeTreeRoutes.WELCOME
            ) {

                composable(LifeTreeRoutes.WELCOME) {
                    WelcomeScreen(
                        onContinue = {
                            navController.navigate(
                                LifeTreeRoutes.LOGIN
                            )
                        }
                    )
                }

                composable(LifeTreeRoutes.LOGIN) {
                    LoginScreen(
                        viewModelFactory =
                            AppModule.provideLoginViewModelFactory(context),

                        onLoginSuccess = {
                            navController.navigate(
                                LifeTreeRoutes.DASHBOARD
                            ) {
                                popUpTo(
                                    LifeTreeRoutes.WELCOME
                                ) {
                                    inclusive = true
                                }
                            }
                        },

                        onRegister = {
                            navController.navigate(
                                LifeTreeRoutes.REGISTER
                            )
                        },

                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(LifeTreeRoutes.REGISTER) {
                    RegisterScreen(
                        viewModelFactory =
                            AppModule.provideRegisterViewModelFactory(context),

                        onRegisterSuccess = {
                            navController.navigate(
                                LifeTreeRoutes.DASHBOARD
                            ) {
                                popUpTo(
                                    LifeTreeRoutes.WELCOME
                                ) {
                                    inclusive = true
                                }
                            }
                        },

                        onLogin = {
                            navController.popBackStack()
                        },

                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(LifeTreeRoutes.DASHBOARD) {
                    DashboardScreen()
                }
            }
        }
    }
}