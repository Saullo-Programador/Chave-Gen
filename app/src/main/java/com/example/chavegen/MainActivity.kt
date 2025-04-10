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
    import androidx.compose.runtime.SideEffect
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.navigation.compose.rememberNavController
    import com.example.chavegen.ui.navigation.RootNavigationGraph
    import com.example.chavegen.ui.navigation.navigateToAuthGraph
    import com.example.chavegen.ui.navigation.navigateToHomeGraph
    import com.example.chavegen.ui.theme.ChaveGenTheme
    import com.example.chavegen.ui.viewModel.AppState
    import com.example.chavegen.ui.viewModel.AppViewModel
    import com.google.accompanist.systemuicontroller.rememberSystemUiController
    import dagger.hilt.android.AndroidEntryPoint

    @AndroidEntryPoint
    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContent {
                var isSystemTheme by rememberSaveable { mutableStateOf(true) }
                ChaveGenTheme(
                    dynamicColor = false,
                    darkTheme = isSystemTheme

                ){
                    val systemUiController = rememberSystemUiController()
                    SideEffect {
                        systemUiController.setStatusBarColor(
                            color = if(isSystemTheme)Color(0xFF000000) else Color(0xFFFFFFFF),
                            darkIcons = isSystemTheme
                        )
                    }
                    Surface(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.statusBars)
                            .fillMaxSize()
                    ) {
                        val navController = rememberNavController()


                        RootNavigationGraph(
                            navController = navController,
                            onThemeToggle ={
                                isSystemTheme = !isSystemTheme
                            }
                        )
                    }
                }
            }
        }
    }
