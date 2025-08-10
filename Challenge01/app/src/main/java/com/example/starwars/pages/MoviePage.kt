package com.example.starwars.pages

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
import com.example.starwars.viewmodels.MovieViewModel

@Composable
fun MoviePage(movieViewModel: MovieViewModel) {

    LaunchedEffect(Unit) {
        movieViewModel.getMovies()
    }

    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = stringResource(R.string.movies),
            modifier = Modifier.padding(10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        for (movie in movieViewModel.movies.value) {
            Text(
                text = movie.title,
                modifier = Modifier.padding(10.dp, 2.dp),
                fontSize = 25.sp
            )
        }
    }
}
