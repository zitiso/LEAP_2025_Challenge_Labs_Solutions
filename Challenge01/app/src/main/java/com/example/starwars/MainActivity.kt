package com.example.starwars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.starwars.pages.HomePage
import com.example.starwars.pages.MoviePage
import com.example.starwars.pages.PeoplePage
import com.example.starwars.pages.PlanetPage
import com.example.starwars.ui.theme.StarWarsTheme
import com.example.starwars.viewmodels.MovieViewModel
import com.example.starwars.viewmodels.PeopleViewModel
import com.example.starwars.viewmodels.PlanetViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarWarsTheme(useDarkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyAppUI()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppUI() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navHostController = rememberNavController()
    val drawerBackgroundColor = colorResource(id = R.color.white)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MyDrawer(
                onDrawerClose = {
                    scope.launch { drawerState.close() }
                },
                navController = navHostController,
                backgroundColor = drawerBackgroundColor
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            modifier = Modifier.clickable {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    MyNavHost(navController = navHostController)
                }
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = stringResource(id = R.string.copyright),
                        textAlign = TextAlign.Center
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navHostController.popBackStack() },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        )
    }
}

@Composable
fun MyDrawer(
    onDrawerClose: () -> Unit,
    navController: NavHostController,
    backgroundColor: Color
) {
    Column (
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth(0.5f)
            .fillMaxHeight()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Image(
                painter = painterResource(id = R.drawable.vader),
                contentDescription = "Lord Vader",
                modifier = Modifier.fillMaxSize().padding(25.dp)
            )
        }
        Text(
            text = "Home",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    navController.navigate("home")
                    onDrawerClose()
                }
        )
        Text(
            text = stringResource(id = R.string.movies),
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    navController.navigate("movies")
                    onDrawerClose()
                }
        )
        Text(
            text = stringResource(id = R.string.people),
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    navController.navigate("people")
                    onDrawerClose()
                }
        )
        Text(
            text = stringResource(id = R.string.planets),
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    navController.navigate("planets")
                    onDrawerClose()
                }
        )
    }
}

@Composable
fun MyNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomePage()
        }
        val movieViewModel = MovieViewModel()
        composable("movies") {
            MoviePage(movieViewModel)
        }
        val peopleViewModel = PeopleViewModel()
        composable("people") {
            PeoplePage(peopleViewModel)
        }
        val planetViewModel = PlanetViewModel()
        composable("planets") {
            PlanetPage(planetViewModel)
        }
    }
}
