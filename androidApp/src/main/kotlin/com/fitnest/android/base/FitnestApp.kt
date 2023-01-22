package com.fitnest.android.base

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.fitnest.android.di.splashModule
import com.fitnest.android.internal.SnackbarDelegate
import com.fitnest.android.screen.login.LoginScreen
import com.fitnest.android.screen.onboarding.OnboardingScreen
import com.fitnest.android.screen.private_area.activity_tracker.ActivityTrackerScreen
import com.fitnest.android.screen.private_area.home.HomeScreen
import com.fitnest.android.screen.private_area.notification.NotificationsScreen
import com.fitnest.android.screen.private_area.photo.PhotoScreen
import com.fitnest.android.screen.private_area.settings.SettingsScreen
import com.fitnest.android.screen.private_area.tracker.TrackerScreen
import com.fitnest.android.screen.proxy.ProxyScreen
import com.fitnest.android.screen.registration.complete_account.CompleteAccountRegistrationScreen
import com.fitnest.android.screen.registration.create_account.CreateAccountRegistrationScreen
import com.fitnest.android.screen.registration.goal.GoalRegistrationScreen
import com.fitnest.android.screen.registration.welcome_back.WelcomeBackRegistrationScreen
import com.fitnest.android.screen.splash.SplashScreen
import com.fitnest.android.style.FitnestTheme
import com.fitnest.domain.enum.FlowType
import com.google.accompanist.navigation.animation.AnimatedComposeNavigator
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import org.kodein.di.compose.rememberInstance
import kotlin.time.ExperimentalTime

@ExperimentalMaterial3Api
@ExperimentalTime
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun FitnestApp(startDestination: String = Route.Splash.screenName) {
    val navController = rememberAnimatedNavController(AnimatedComposeNavigator())
    val snackbarDelegate: SnackbarDelegate by rememberInstance()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    snackbarDelegate.apply {
        this.snackbarHostState = snackbarHostState
        coroutineScope = rememberCoroutineScope()
    }

    FitnestTheme {
        Scaffold(
            bottomBar = { BottomBar(navController) },
            topBar = { TopBar(navController) },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) {
                    val backgroundColor = snackbarDelegate.snackbarBackgroundColor
                    Snackbar(
                        snackbarData = it,
                        containerColor = backgroundColor,
                        modifier = Modifier.testTag(snackbarDelegate.snackbarTestTag)
                    )
                }
            }
        ) {
            AnimatedNavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(it)
            ) {
                composable(route = Route.Splash.screenName) {
                    SplashScreen(
                        navController = navController,
                        diSubModule = splashModule
                    )
                }
                composable(route = Route.Login.screenName) {
                    LoginScreen(navController = navController)
                }
                composable(route = "proxy/{flowType}", arguments = listOf(navArgument("flowType") {
                    type = NavType.EnumType(type = FlowType::class.java)
                })) {
                    val flowType = it.arguments?.getSerializable("flowType") as FlowType
                    ProxyScreen(navController = navController, flowType)
                }
                composable(
                    route = "onboardingStep/{stepName}",
                    arguments = listOf(navArgument("stepName") { type = NavType.StringType }),
                    enterTransition = {
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300))
                    },
                    exitTransition = {
                        slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300))
                    },
                    popEnterTransition = {
                        slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(300))
                    },
                    popExitTransition = {
                        slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300))
                    },
                ) {
                    OnboardingScreen(
                        navController = navController,
                        stepName = it.arguments?.getString("stepName").orEmpty()
                    )
                }
                composable(
                    route = "registrationStep/{stepName}",
                    arguments = listOf(
                        navArgument("stepName") { type = NavType.StringType },
                    ),
                    enterTransition = {
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300))
                    },
                    exitTransition = {
                        slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300))
                    },
                    popEnterTransition = {
                        slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(300))
                    },
                    popExitTransition = {
                        slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300))
                    },
                ) {
                    when (it.arguments?.getString("stepName").orEmpty()) {
                        "STEP_CREATE_ACCOUNT" -> {
                            CreateAccountRegistrationScreen(navController = navController)
                        }
                        "STEP_COMPLETE_ACCOUNT" -> {
                            CompleteAccountRegistrationScreen(navController = navController)
                        }
                        "STEP_GOAL" -> {
                            GoalRegistrationScreen(navController = navController)
                        }
                        "STEP_WELCOME_BACK" -> {
                            WelcomeBackRegistrationScreen(navController = navController)
                        }
                    }
                }
                composable(route = Route.PrivateAreaHome.screenName) {
                    HomeScreen(navController = navController)
                }
                composable(route = Route.PrivateAreaSettings.screenName) {
                    SettingsScreen(navController = navController)
                }
                composable(route = Route.PrivateAreaPhoto.screenName) {
                    PhotoScreen()
                }
                composable(route = Route.PrivateAreaTracker.screenName) {
                    TrackerScreen()
                }
                composable(route = Route.PrivateAreaNotifications.screenName) {
                    NotificationsScreen()
                }
                composable(route = Route.PrivateAreaActivityTracker.screenName) {
                    ActivityTrackerScreen()
                }
            }
        }
    }
}