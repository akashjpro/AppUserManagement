package com.example.apptest.presentation.navigator

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.apptest.R
import com.example.apptest.domain.model.User
import com.example.apptest.presentation.addScreen.AddScreen
import com.example.apptest.presentation.addScreen.AddViewModel
import com.example.apptest.presentation.details.DetailViewModel
import com.example.apptest.presentation.details.DetailsScreen
import com.example.apptest.presentation.editScreen.EditScreen
import com.example.apptest.presentation.editScreen.EditViewModel
import com.example.apptest.presentation.home.HomeScreen
import com.example.apptest.presentation.home.HomeViewModel
import com.example.apptest.presentation.navgraph.Route
import com.example.apptest.presentation.navigator.components.BottomNavigation
import com.example.apptest.presentation.navigator.components.BottomNavigationItem
import com.example.apptest.presentation.saved.SavedScreen
import com.example.apptest.presentation.saved.SavedViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_add, text = "Add"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Saved"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.AddScreen.route -> 1
        Route.SavedScreen.route -> 2
        else -> 0
    }

    //Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.AddScreen.route ||
                backStackState?.destination?.route == Route.SavedScreen.route
    }


    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            BottomNavigation(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController,
                            route = Route.HomeScreen.route
                        )

                        1 -> navigateToTab(
                            navController = navController,
                            route = Route.AddScreen.route
                        )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Route.SavedScreen.route
                        )
                    }
                }
            )
        }
    }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()

                val isRefresh =
                    navController.previousBackStackEntry?.savedStateHandle?.get<Boolean>("isRefresh")

                HomeScreen(
                    viewModel = viewModel,
                    navigateToDetails = { user ->
                        navigateToDetails(
                            navController = navController,
                            user = user
                        )
                    },
                    navigateToEdit = { user ->
                        navigateToEdit(
                            navController = navController,
                            user = user
                        )
                    },
                    isRefresh = isRefresh ?: false
                )

            }
            composable(route = Route.AddScreen.route) {
                val viewModel: AddViewModel = hiltViewModel()
                AddScreen(
                    viewModel = viewModel,
                    navigateToHome = {
                        navigateToTab(
                            navController = navController,
                            route = Route.HomeScreen.route,
                            isRefresh = true
                        )
                    }
                )
            }
            composable(route = Route.DetailsScreen.route) {
                val viewModel: DetailViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<User?>("user")
                    ?.let { user ->
                        DetailsScreen(
                            userInitial = user,
                            navigateUp = { navController.navigateUp() },
                            viewModel = viewModel

                        )
                    }

            }
            composable(route = Route.EditScreen.route) {
                val viewModel: EditViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<User?>("user")
                    ?.let { user ->
                        EditScreen(
                            user = user,
                            navigateUp = { navController.navigateUp() },
                            viewModel = viewModel,
                            navigateToHome = {
                                navigateToTab(
                                    navController = navController,
                                    route = Route.HomeScreen.route,
                                    isRefresh = true
                                )
                            }
                        )
                    }

            }
            composable(route = Route.SavedScreen.route) {
                val viewModel: SavedViewModel = hiltViewModel()
                SavedScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = Route.HomeScreen.route
        )
    }
}

private fun navigateToTab(navController: NavController, route: String, isRefresh: Boolean = false) {
    navController.currentBackStackEntry?.savedStateHandle?.set("isRefresh", isRefresh)
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToDetails(navController: NavController, user: User) {
    navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}

private fun navigateToEdit(navController: NavController, user: User) {
    navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
    navController.navigate(
        route = Route.EditScreen.route
    )
}