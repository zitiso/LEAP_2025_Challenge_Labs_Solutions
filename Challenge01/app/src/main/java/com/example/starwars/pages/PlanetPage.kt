package com.example.starwars.pages

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
import com.example.starwars.viewmodels.PlanetViewModel


@Composable
fun PlanetPage(planetViewModel: PlanetViewModel) {

    LaunchedEffect(Unit) {
        planetViewModel.getPlanets()
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()) // enables scrolling
    ) {
        Text(
            text = stringResource(R.string.planets),
            modifier = Modifier.padding(10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        for (planet in planetViewModel.planets.value) {
            Text(
                text = planet.name,
                modifier = Modifier.padding(10.dp, 2.dp),
                fontSize = 25.sp
            )
        }
    }
}