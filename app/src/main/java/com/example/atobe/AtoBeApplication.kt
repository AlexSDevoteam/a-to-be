package com.example.atobe

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.atobe.nav.destination.Details
import com.example.atobe.nav.destination.Home
import com.example.atobe.nav.graph.ProductNavHost
import com.example.atobe.ui.theme.AtoBeTheme
import com.example.atobe.ui.utils.AtoBeTopBar
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AtoBeApplication @Inject constructor() : Application()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtoBeApp() {
    AtoBeTheme {
        val navController = rememberNavController()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination
        val currentScreenTitle = when (currentDestination?.route) {
            Home.route -> Home.name
            Details.route -> Details.name
            else -> stringResource(R.string.atobeapp)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AtoBeTopBar(
                    canNavigateBack = currentBackStackEntry != null && currentDestination?.route != Home.route,
                    navigateUp = { navController.navigateUp() },
                    currentScreenTitle = currentScreenTitle,
                    scrollBehavior = scrollBehavior,
                )
            },
            content = {
                ProductNavHost(
                    navHostController = navController,
                    modifier = Modifier
                        .padding(it)
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                )
            }
        )
    }
}
