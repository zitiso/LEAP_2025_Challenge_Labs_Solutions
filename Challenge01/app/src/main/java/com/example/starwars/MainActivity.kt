package com.example.starwars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.starwars.R
import com.example.starwars.pages.HomePage
import com.example.starwars.pages.MoviePage
import com.example.starwars.pages.PeoplePage
import com.example.starwars.pages.PlanetPage
import com.example.starwars.viewmodels.MovieViewModel
import com.example.starwars.viewmodels.PeopleViewModel
import com.example.starwars.viewmodels.PlanetViewModel
import kotlinx.coroutines.launch

private const val START_ROUTE = "home"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MyAppUI()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppUI() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MyDrawer(
                onDrawerClose = { scope.launch { drawerState.close() } },
                navController = navController,
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable { scope.launch { drawerState.open() } }
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = stringResource(id = R.string.copyright),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            // FAB shows whenever we're NOT on the start route
            floatingActionButton = {
                if (currentRoute != START_ROUTE) {
                    FloatingActionButton(
                        onClick = {
                            // Try to go back; if nothing to pop (because we popped up to start), go "home".
                            val wentBack = navController.navigateUp()
                            if (!wentBack) {
                                navController.navigate(START_ROUTE) {
                                    launchSingleTop = true
                                    popUpTo(START_ROUTE) { inclusive = false }
                                }
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { padding ->
            Box(Modifier.padding(padding)) {
                MyNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun MyDrawer(
    onDrawerClose: () -> Unit,
    navController: NavHostController,
    backgroundColor: Color
) {
    Column(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth(0.6f)
            .fillMaxHeight()
            .padding(top = 24.dp)
    ) {
        // Helper to navigate without stacking duplicates, and keep start as the root.
        fun go(route: String) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(START_ROUTE) { saveState = true }
            }
            onDrawerClose()
        }

        DrawerItem("Home") { go(START_ROUTE) }
        DrawerItem(stringResource(R.string.movies)) { go("movies") }
        DrawerItem(stringResource(R.string.people)) { go("people") }
        DrawerItem(stringResource(R.string.planets)) { go("planets") }
    }
}

@Composable
private fun DrawerItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
fun MyNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = START_ROUTE) {
        composable(START_ROUTE) {
            HomePage()
//            Text("Home", modifier = Modifier
//                .fillMaxSize()
//                .wrapContentSize(Alignment.Center))
        }
        composable("movies") {
             val vm: MovieViewModel = viewModel()
             MoviePage(vm)
//            Text("Movies", modifier = Modifier
//                .fillMaxSize()
//                .wrapContentSize(Alignment.Center))
        }
        composable("people") {
             val vm: PeopleViewModel = viewModel()
             PeoplePage(vm)
//            Text("People", modifier = Modifier
//                .fillMaxSize()
//                .wrapContentSize(Alignment.Center))
        }
        composable("planets") {
             val vm: PlanetViewModel = viewModel()
             PlanetPage(vm)
//            Text("Planets", modifier = Modifier
//                .fillMaxSize()
//                .wrapContentSize(Alignment.Center))
        }
    }
}
