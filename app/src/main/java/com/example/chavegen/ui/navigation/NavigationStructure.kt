package com.example.chavegen.ui.navigation


object AuthGraph {
    const val ROOT = "auth_graph"
    const val SIGN_IN = "sign_in"
    const val SIGN_UP = "sign_up"
    const val FORGOT = "forgot"
}

object HomeGraph {
    const val ROOT = "home_graph"
    const val HOME = "home"
    const val REGISTER = "register"
    const val SETTINGS = "settings"
    const val EDIT_LOGIN_BASE = "edit_login"
    const val EDIT_LOGIN = "$EDIT_LOGIN_BASE/{documentId}"
}

object RootGraph {
    const val ROOT = "root_graph"
    const val SPLASH = "splash"
}

object AppGraph {
    val auth = AuthGraph
    val home = HomeGraph
    val initial = RootGraph
}