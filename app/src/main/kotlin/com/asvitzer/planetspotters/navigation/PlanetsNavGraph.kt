package com.asvitzer.planetspotters.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.asvitzer.planetspotters.R
import com.asvitzer.planetspotters.navigation.PlanetsDestinationsArgs.PLANET_ID_ARG
import com.asvitzer.planetspotters.navigation.PlanetsDestinationsArgs.TITLE_ARG
import com.asvitzer.planetspotters.navigation.PlanetsDestinationsArgs.USER_MESSAGE_ARG
import com.asvitzer.planetspotters.ui.screen.AddEditPlanetScreen
import com.asvitzer.planetspotters.ui.screen.PlanetsListScreen

@Composable
fun PlanetsNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = PlanetsDestinations.PLANETS_ROUTE,
    navActions: PlanetsNavigationActions = remember(navController) {
        PlanetsNavigationActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            PlanetsDestinations.PLANETS_ROUTE,
            arguments = listOf(
                navArgument(USER_MESSAGE_ARG) { type = NavType.IntType; defaultValue = 0 }
            )
        ) {
            PlanetsListScreen(
                addPlanet = { navActions.navigateToAddEditPlanet(R.string.add_planet, null) },
                editPlanet = { planetId -> navActions.navigateToAddEditPlanet(R.string.edit_planet, planetId) }
            )
        }
        composable(
            PlanetsDestinations.ADD_EDIT_PLANET_ROUTE,
            arguments = listOf(
                navArgument(TITLE_ARG) { type = NavType.IntType },
                navArgument(PLANET_ID_ARG) { type = NavType.StringType; nullable = true },
            )
        ) { entry ->
            val planetId = entry.arguments?.getString(PLANET_ID_ARG)
            AddEditPlanetScreen(
                onPlanetUpdate = {
                    navActions.navigateToPlanets(
                        if (planetId == null) ADD_EDIT_RESULT_OK else EDIT_RESULT_OK
                    )
                },
            )
        }
    }
}

// Keys for navigation
const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 2