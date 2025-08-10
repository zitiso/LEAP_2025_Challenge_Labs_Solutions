package com.example.starwars.people.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.starwars.people.viewmodels.MasterViewModel
import com.example.starwars.people.viewmodels.SharedViewModel
import com.example.starwars.starwars.people.R

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MasterPage(
    personViewModel: MasterViewModel,
    sharedViewModel: SharedViewModel,
    navHostController: NavHostController) {

    val people by personViewModel.people.observeAsState(emptyList())

    LaunchedEffect(Unit, block = {
        personViewModel.getPeople()
    })
    if (people.isEmpty()) {
        // Show a lightweight placeholder while loading
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                ) {
                    Column(
                        modifier = Modifier.padding(5.dp)
                    ) {

                        Box(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                            ) {
                                items(people) { person ->
                                    ListItem(
                                        headlineContent = {
                                            Text(
                                                person.name,
                                                fontWeight = FontWeight.Bold
                                            )
                                        },
                                        supportingContent = {
                                            Column {
                                                Text(person.gender)
                                                Text("""hair: ${person.hair_color}  eyes: ${person.eye_color}""")

                                                var cm = "cm"
                                                if (person.height == "unknown") {
                                                    cm = ""
                                                }

                                                var kg = "kg"
                                                if (person.mass == "unknown") {
                                                    kg = ""
                                                }

                                                Text("""height: ${person.height}${cm}  mass: ${person.mass}${kg}""")
                                            }
                                        },

                                        colors = ListItemDefaults.colors(headlineColor = Color.Black),

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                            .shadow(5.dp)
                                            .combinedClickable(
                                                onClick = {
                                                    val regex = """\d+""".toRegex()
                                                    val id: Int? =
                                                        regex.find(person.url)?.value?.toInt()
                                                    if (id != null) {
                                                        sharedViewModel.updateInt(id)
                                                    }
                                                    navHostController.navigate("detail")
                                                }
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}