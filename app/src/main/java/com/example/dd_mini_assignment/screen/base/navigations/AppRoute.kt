package com.example.dd_mini_assignment.screen.base.navigations

import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import com.example.dd_mini_assignment.R

sealed class RouteSection(val graph: Int) {
    object Home : RouteSection(R.navigation.nav_graph)
}

sealed class RouteDestination(@IdRes val destination: Int) {

    object Back : RouteDestination(-1)

    sealed class Order(@IdRes destination: Int) : RouteDestination(destination) {
        object Ingredient : Order(R.id.ingredientFragment)
    }
}

val defaultNavOptions: NavOptions
    get() {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_from_right)
            .setExitAnim(R.anim.slide_to_left)
            .setPopEnterAnim(R.anim.slide_from_left)
            .setPopExitAnim(R.anim.slide_to_right)
            .build()
    }