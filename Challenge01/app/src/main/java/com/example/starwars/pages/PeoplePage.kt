package com.example.starwars.pages

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starwars.R
import com.example.starwars.viewmodels.PeopleViewModel

@Composable
fun PeoplePage(personViewModel: PeopleViewModel) {

    // Trigger load of people when the page first appears
    LaunchedEffect(Unit) {
        personViewModel.getPeople()
    }

    // Get the list of people from the ViewModel
    val people = try {
        personViewModel.people.value
    } catch (_: Throwable) {
        Log.e("People", "No people")
        emptyList()
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()) // Makes the whole column scrollable
    ) {
        // Title
        Text(
            text = stringResource(R.string.people),
            modifier = Modifier.padding(10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        // Display each person
        for (person in people) {
            Text(
                text = person.name,
                modifier = Modifier.padding(10.dp, 2.dp),
                fontSize = 25.sp
            )
        }
    }
}
