/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.nav.destination

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface ProductDestination {
    val route: String
    val name: String
}

object Home : ProductDestination {
    override val route = "Home"
    override val name = "Home"
}

object Details : ProductDestination {
    override val route = "Details/{id}"
    override val name = "Product Details"
    val arguments = listOf(
        navArgument("id") {
            type = NavType.IntType
        }
    )
    fun route(id: Int) = "Details/$id"
}