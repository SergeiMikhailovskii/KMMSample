package com.mikhailovskii.kmmtest.android.base

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mikhailovskii.kmmtest.android.style.FitnestTheme
import com.mikhailovskii.kmmtest.android.view.login.LoginScreen
import com.mikhailovskii.kmmtest.android.view.onboarding.OnboardingScreen
import com.mikhailovskii.kmmtest.android.view.splash.SplashScreen

@ExperimentalAnimationApi
@Composable
fun FitnestApp() {
    val navController = rememberAnimatedNavController()

    FitnestTheme {
        Scaffold {
            AnimatedNavHost(
                navController = navController,
                startDestination = Route.Splash.screenName
            ) {
                composable(route = Route.Splash.screenName) {
                    SplashScreen(navController = navController)
                }
                composable(route = Route.Login.screenName) {
                    LoginScreen()
                }
                composable(
                    route = "onboarding/{progress}",
                    arguments = listOf(navArgument("progress") { type = NavType.IntType }),
                    enterTransition = { _, _ ->
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(700))
                    },
                    exitTransition = { _, _ ->
                        slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(700))
                    },
                    popEnterTransition = { _, _ ->
                        slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(700))
                    },
                    popExitTransition = { _, _ ->
                        slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(700))
                    },
                ) {
                    OnboardingScreen(
                        navController = navController,
                        progress = it.arguments?.getInt("progress") ?: 1
                    )
                }
            }
        }
    }
}