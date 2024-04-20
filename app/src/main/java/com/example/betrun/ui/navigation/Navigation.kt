package com.example.betrun.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.betrun.domain.model.questions.PersonData
import com.example.betrun.ui.screens.auth.LoginScreen
import com.example.betrun.ui.screens.auth.OnboardingContent
import com.example.betrun.ui.screens.auth.OnboardingHolder
import com.example.betrun.ui.screens.auth.RegisterScreen
import com.example.betrun.ui.screens.home.HomeScreen
import com.example.betrun.ui.screens.home.ProfileScreen
import com.example.betrun.ui.screens.home.ProgramScreen
import com.example.betrun.ui.screens.home.WorkoutScreen
import com.example.betrun.ui.screens.questions.QuestionsFinalScreen
import com.example.betrun.ui.screens.questions.QuestionsNavScreen
import com.example.betrun.ui.viewmodels.auth.AuthenticationNavigationViewModel
import com.example.betrun.ui.viewmodels.home.HomeViewModel
import com.example.betrun.ui.viewmodels.questions.QuestionsViewModel
import com.example.betrun.ui.viewmodels.questions.QuestionsViewModelFactory
import com.example.betrun.util.navigation.Destination
import com.example.betrun.util.navigation.Graphs
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BetrunNavHost(
    navController: NavHostController = rememberNavController(),
    lifecycleScope: LifecycleCoroutineScope,
    authenticationNavigationViewModel : AuthenticationNavigationViewModel = koinViewModel(),
    viewModel: QuestionsViewModel = viewModel(
        factory = QuestionsViewModelFactory()
    )
) {
    NavHost(
        navController = navController,
        route = Graphs.ROOT,
        startDestination =
            if (authenticationNavigationViewModel.isLoggedInState.value)
                Graphs.AUTH
            else
                Graphs.MAIN
    ) {
        authNavGraph(navController, viewModel)
        composable(route = Graphs.MAIN) {
            HomeScreen(lifecycleScope)
        }
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    questionsViewModel: QuestionsViewModel
) {
    navigation(
        route = Graphs.AUTH,
        startDestination = Destination.Welcome.route
    ) {
        composable(Destination.Welcome.route) {
            OnboardingHolder {
                OnboardingContent(
                    modifier = it,
                    onQuestionsNext = {
                        navController.navigate(Destination.Register.route)
                    }
                )
            }
        }
        composable(
            route = Destination.Login.route
        ) {
            LoginScreen(navHostController = navController)
        }
        composable(
            route = Destination.Register.route
        ) {
            RegisterScreen(navHostController = navController)
        }
        composable(Destination.QuestionsProgress.route) {
            QuestionsNavScreen(
                onQuestionsComplete = {
                    navController.navigate(Destination.StartRoute.route)
                },
                onNavUp = navController::navigateUp,
                viewModel = questionsViewModel
            )
        }
        composable(Destination.StartRoute.route) {
            val viewModel = koinViewModel<HomeViewModel>()
            QuestionsFinalScreen(
                onDonePressed = {
                    viewModel.setPersonData(
                        personData = PersonData(
                            currentWeight = questionsViewModel.currentWeightResponse,
                            weightGoal = questionsViewModel.weightGoalResponse,
                            height = questionsViewModel.heightResponse,
                            name = questionsViewModel.nameResponse
                        ),
                        unit = {
                            navController.navigate(Graphs.MAIN) {
                                popUpTo(Graphs.AUTH) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    lifecycleScope: LifecycleCoroutineScope
){
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graphs.MAIN,
        startDestination = Destination.Program.route
    ) {
        composable(Destination.Program.route) {
            ProgramScreen()
        }
        composable(
            route = Destination.Workout.route,
            deepLinks = Destination.Workout.deepLinks
        ) {
            WorkoutScreen()
        }
        composable(Destination.Profile.route) {
            ProfileScreen(
                lifecycleScope = lifecycleScope,
                navHostController = navController
            )
        }
    }
}