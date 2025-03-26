package com.example.chavegen.ui.navigation


object AuthGraph {
    const val ROOT = "auth_graph"
    const val SIGN_IN = "sign_in"
    const val SIGN_UP = "sign_up"
}

object MainGraph {
    const val ROOT = "main_graph"
    const val MAIN = "main"
}

object SplashGraph {
    const val SPLASH = "splash"
}

object RootGraph {
    const val ROOT = "root_graph"
}

object AppGraph {
    val splash = SplashGraph
    val auth = AuthGraph
    val main = MainGraph
    val initial = RootGraph
}