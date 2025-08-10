package com.example.starwars.people

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.starwars.people.pages.DetailPage
import com.example.starwars.people.pages.MasterPage
import com.example.starwars.people.ui.theme.StarWarsPeopleTheme
import com.example.starwars.people.viewmodels.DetailViewModel
import com.example.starwars.people.viewmodels.MasterViewModel
import com.example.starwars.people.viewmodels.SharedViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarWarsPeopleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val sharedViewModel :SharedViewModel = viewModel()
                    MyAppUI(sharedViewModel)
                }
            }
        }
    }
}

@Composable
fun MyAppUI(sharedViewModel: SharedViewModel) {
    val navHostController = rememberNavController()
    MyNavHost(
        navController = navHostController,
        sharedViewModel = sharedViewModel
    )
}

@Composable
fun MyNavHost(sharedViewModel :SharedViewModel, navController: NavHostController) {

    val masterViewModel = MasterViewModel()
    val detailViewModel = DetailViewModel()

    NavHost(
        navController = navController,
        startDestination = "master"
    ) {

        composable("master") {
            MasterPage(masterViewModel, sharedViewModel, navController)
        }

        composable("detail") {
            DetailPage(detailViewModel, sharedViewModel, navController)
        }


    }
}
