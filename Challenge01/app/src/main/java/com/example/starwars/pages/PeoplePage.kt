package com.example.starwars.pages

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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

    LaunchedEffect(Unit) {
        personViewModel.getPeople()
    }

    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = stringResource(R.string.people),
            modifier = Modifier.padding(10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        for (person in personViewModel.people.value) {
            Text(
                text = person.name,
                modifier = Modifier.padding(10.dp, 2.dp),
                fontSize = 25.sp
            )
        }
    }
}
