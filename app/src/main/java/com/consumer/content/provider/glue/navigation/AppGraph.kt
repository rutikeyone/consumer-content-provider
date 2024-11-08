package com.consumer.content.provider.glue.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.consumer.content.core.presentation.assistedViewModel
import com.consumer.content.humans.presentation.details.HumanDetailsPage
import com.consumer.content.humans.presentation.details.viewmodel.HumanDetailsViewModel
import com.consumer.content.humans.presentation.list.HumanListPage
import com.consumer.content.provider.glue.humans.requiredHumanDetailsViewModelFactory

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.List.route,
    ) {
        composable(NavigationItem.List.route) {
            HumanListPage(
                onClickItem = {
                    val route = "${Screen.Details.name}/${it.id}"

                    navController.navigate(route)
                }
            )
        }

        composable(
            NavigationItem.Details.route,
            arguments = listOf(
                navArgument(NavigationItem.Details.ARG_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val humanStringId =
                requireNotNull(it.arguments?.getString(NavigationItem.Details.ARG_ID))
            val humanLongId = requireNotNull(humanStringId.toLongOrNull())

            val factory = requiredHumanDetailsViewModelFactory()

            val viewModel = assistedViewModel<HumanDetailsViewModel> {
                factory.create(humanLongId)
            }

            HumanDetailsPage(
                viewModel = viewModel,
            )
        }
    }
}