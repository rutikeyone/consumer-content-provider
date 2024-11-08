package com.consumer.content.provider.glue.navigation

enum class Screen {
    List,
    Details,
}

sealed class NavigationItem(val route: String) {
    data object List : NavigationItem(Screen.List.name)
    data object Details : NavigationItem("${Screen.Details.name}/{id}") {
        const val ARG_ID = "id"
    }
}