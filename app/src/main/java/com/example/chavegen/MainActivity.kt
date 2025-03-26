    package com.example.chavegen

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.enableEdgeToEdge
    import androidx.compose.foundation.layout.WindowInsets
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.statusBars
    import androidx.compose.foundation.layout.windowInsetsPadding
    import androidx.compose.material3.Surface
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Modifier
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.rememberNavController
    import com.example.chavegen.ui.navigation.AppGraph
    import com.example.chavegen.ui.navigation.authGraph
    import com.example.chavegen.ui.navigation.homeGraph
    import com.example.chavegen.ui.navigation.navigateToAuthGraph
    import com.example.chavegen.ui.navigation.navigateToHomeGraph
    import com.example.chavegen.ui.navigation.navigateToSignIn
    import com.example.chavegen.ui.navigation.navigateToSignUp
    import com.example.chavegen.ui.navigation.splashScreen
    import com.example.chavegen.ui.theme.ChaveGenTheme
    import com.example.chavegen.ui.viewModel.AppState
    import com.example.chavegen.ui.viewModel.AppViewModel
    import dagger.hilt.android.AndroidEntryPoint

    @AndroidEntryPoint
    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContent {
                ChaveGenTheme {
                    Surface(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.statusBars)
                            .fillMaxSize()
                    ) {

                        val navController = rememberNavController()
                        val appViewModel: AppViewModel = hiltViewModel()
                        val appState by appViewModel.state
                            .collectAsState(initial = AppState())

                        LaunchedEffect(appState) {
                            if(appState.isInitLoading){
                                return@LaunchedEffect
                            }
                            appState.user?.let {
                                navController.navigateToHomeGraph()
                            }?: navController.navigateToAuthGraph()
                        }
                        NavHost(
                            navController = navController,
                            route = AppGraph.initial.ROOT,
                            startDestination = if (appState.user != null) AppGraph.main.ROOT else AppGraph.auth.ROOT
                        ) {
                            splashScreen()
                            authGraph(
                                onNavigateToSignIn = {
                                    navController.navigateToSignIn(it)
                                },
                                onNavigateToSignUp = {
                                    navController.navigateToSignUp()
                                }
                            )
                            homeGraph(navController = navController)
                        }
                    }
                }
            }
        }
    }