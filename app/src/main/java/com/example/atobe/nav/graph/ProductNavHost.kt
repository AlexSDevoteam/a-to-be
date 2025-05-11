/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.nav.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.atobe.nav.destination.Details
import com.example.atobe.nav.destination.Home
import com.example.atobe.ui.details.DetailsView
import com.example.atobe.ui.home.HomeView

@Composable
fun ProductNavHost(navHostController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navHostController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeView(
                modifier = modifier,
                onDetailsClick = {
                    navHostController.navigate(Details.route(id = it))
                })
        }
        composable(
            route = Details.route,
            arguments = Details.arguments
        ) {
            DetailsView(
                viewModel = hiltViewModel(),
                id = it.arguments?.getInt("id") ?: 1,
                modifier = modifier
            )
        }
    }
}