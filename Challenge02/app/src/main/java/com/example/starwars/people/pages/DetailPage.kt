package com.example.starwars.people.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.starwars.people.data.Person
import com.example.starwars.people.viewmodels.DetailViewModel
import com.example.starwars.people.viewmodels.SharedViewModel
import com.example.starwars.starwars.people.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(detailViewModel: DetailViewModel, sharedViewModel: SharedViewModel, navHostController: NavHostController) {

    // These two values are shared using two different APIs
    //
    //   person using MutableLiveData
    //   sharedId using Flow
    //
    // Both allow this Composable to be updated when the values
    // change in their respective ViewModels

    val sharedId by sharedViewModel.sharedId.collectAsState()
    val person by detailViewModel.person.observeAsState(null)

    LaunchedEffect(Unit, block = {
        detailViewModel.getPerson(sharedId)
    })

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
                    .padding(15.dp)
            ) {
                Card(
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                        .shadow(5.dp)

                ) {

                    PeopleDetail(person)

                }
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

@Composable
fun PeopleDetail(person : Person?) {

    val textModifier = Modifier.fillMaxWidth()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        if( person == null ) {
            Text("Unknown Person")
        } else {
            Column  {

                Text(
                    person.name,
                    textModifier.padding(bottom = 30.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )

                Text( text = person.gender )
                Text( text = person.birth_year )
                Text( text = """hair: ${person.hair_color}  eyes: ${person.eye_color}""" )

                var cm = "cm"
                if (person.height == "unknown") {
                    cm = ""
                }

                var kg = "kg"
                if (person.mass == "unknown") {
                    kg = ""
                }

                Text("""height: ${person.height}${cm}  mass: ${person.mass}${kg}""")
                Text("""home world: ${person.homeworld}""")
                
                Divider(
                    color = Color.LightGray,
                    thickness = 2.dp,
                    modifier = Modifier.padding(vertical=10.dp))

                Text(text = "Appears in:")

                person.films?.forEach {
                    Text(
                        text=it,
                        Modifier.padding(start=10.dp)
                    )
                }
            }

        }
    }
}