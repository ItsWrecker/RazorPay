package com.wrecker.razorpay

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.wrecker.razorpay.presentatation.MovieDetailsViewModel
import com.wrecker.razorpay.presentatation.SearchAction
import com.wrecker.razorpay.presentatation.SearchViewModel
import com.wrecker.razorpay.ui.theme.RazorPayTheme
import dagger.hilt.android.AndroidEntryPoint


//http://www.omdbapi.com/?i=tt3896198&apikey=d347b563
//https://www.omdbapi.com/?s=spiderman&apikey=d347b563

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RazorPayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = { TopAppBar(title = { Text(text = "MovieApp") }) }) {
                        val searchViewModel by viewModels<SearchViewModel>()
                        val detailsViewModel by viewModels<MovieDetailsViewModel>()

                        val navHost = rememberNavController()
                        NavHost(
                            modifier = Modifier.padding(it),
                            navController = navHost, startDestination = Destination.MainScreen.route
                        ) {
                            composable(Destination.MainScreen.route) {
                                SearchScreen(searchViewModel, navHost)
                            }
                            val movieKey = "imdbId"
                            composable(
                                Destination.DetailsScreen.route + "/{$movieKey}",
                                arguments = listOf(
                                    navArgument(movieKey) {
                                        type = NavType.StringType
                                    }
                                )) {
                                val id = it.arguments?.getString(movieKey)
                                MovieDerailsScreen(detailsViewModel, id)
                            }
                        }
                    }

                }
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    private fun MovieDerailsScreen(detailsViewModel: MovieDetailsViewModel, id: String?) {

        if (id != null) {
            detailsViewModel.getDetails(id)
        }
        val state by detailsViewModel.state.collectAsState()

        if (state.isLoading.not()) {
            Column(verticalArrangement = Arrangement.Top) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    GlideImage(
                        model = state.movie?.poster,
                        contentDescription = "Poster",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp), contentScale = ContentScale.FillWidth
                    )
                }

                Text(text = "Title: ${state.movie?.title}")
                Text(text = "Title: ${state.movie?.year}")
            }

        }


    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
    @Composable
    private fun SearchScreen(searchViewModel: SearchViewModel, navHost: NavHostController) {
        val state by searchViewModel.state.collectAsState()
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val searchInput = remember {
                mutableStateOf("")
            }
            OutlinedTextField(
                value = searchInput.value,
                onValueChange = {
                    searchInput.value = it
                },
                placeholder = { Text(text = "Search movie by title") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            OutlinedButton(onClick = {
                searchViewModel.handleUserEvent(SearchAction.Search(searchInput.value.trim()))
            }, enabled = searchInput.value.isNotEmpty()) {
                Text(text = "Search")
            }

            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 32.dp
                    )
                    .height(1.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                itemsIndexed(state.movieList) { _, item ->
                    OutlinedCard(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navHost.navigate(Destination.DetailsScreen.route +  "/${item.imdbId}")
                        }) {
                        Row(horizontalArrangement = Arrangement.Start) {
                            GlideImage(
                                model = item.poster,
                                contentDescription = "Poster",
                                modifier = Modifier.size(100.dp)
                            )
                            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                                Text(text = "Title: ${item.title}")
                                Text(text = "Year: ${item.year}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RazorPayTheme {
        Greeting("Android")
    }
}