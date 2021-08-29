package com.example.dd_mini_assignment.screen.base

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.dd_mini_assignment.screen.base.navigations.RouteDestination
import com.example.dd_mini_assignment.screen.base.navigations.RouteSection
import com.example.dd_mini_assignment.screen.base.navigations.SingleEvent
import com.example.dd_mini_assignment.screen.base.navigations.defaultNavOptions

open class BaseViewModel: ViewModel() {

    val navigationEvent: MutableLiveData<SingleEvent<NavController.() -> Any>> = MutableLiveData()

    open fun navigateTo(route: RouteSection, args: Bundle? = null) {
        withNavController {
            navigate(route.graph, args, defaultNavOptions)
        }
    }

    open fun navigateToGraph(route: RouteSection, args: Bundle? = null) {
        withNavController(isGraph = true) {
            setGraph(route.graph, args)
        }
    }

    open fun navigateTo(
        route: RouteDestination,
        args: Bundle? = null,
        clearStack: Boolean = false
    ) {
        when {
            route is RouteDestination.Back -> withNavController(route = route) { popBackStack() }
            clearStack -> withNavController(route = route) { popBackStack(route.destination, false) }
            else -> withNavController(route = route) { navigate(route.destination, args, defaultNavOptions) }
        }
    }

    private fun withNavController(
        route: RouteDestination = RouteDestination.Back,
        isGraph: Boolean = false,
        block: NavController.() -> Any
    ) {
        navigationEvent.postValue(
            SingleEvent(block).apply {
                this.routeDestination = route
                this.isGraph = isGraph
            }
        )
    }

    fun popBackStack() {
        navigateTo(RouteDestination.Back)
    }
}