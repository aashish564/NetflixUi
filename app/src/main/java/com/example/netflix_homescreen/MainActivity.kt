package com.example.netflix_homescreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: MainViewModel = viewModel()

            val popularMoviesState by viewModel.popularMovies.collectAsState()
            val onlyOnNetflixState by viewModel.onlyOnNetflix.collectAsState()
            val blockbusterMoviesState by viewModel.blockbusterMovies.collectAsState()
            val popularTvShowsState by viewModel.popularTvShows.collectAsState()



                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                )
                {

//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(color = Color.Black)
//                        .verticalScroll(rememberScrollState())
//                ) {
//                    TopUi()
//                    MovieScreen(
//                        popularMovies = popularMoviesState.list,
//                        onlyOnNetflix = onlyOnNetflixState.list,
//                        blockbusterMovies = blockbusterMoviesState.list,
//                        popularTvShows = popularTvShowsState.list
//                    )
//                }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Black)
                    ) {
                        item {
                            TopUi()
                        }
                        item {
                            MovieScreen(
                                popularMovies = popularMoviesState.list,
                                onlyOnNetflix = onlyOnNetflixState.list,
                                blockbusterMovies = blockbusterMoviesState.list,
                                popularTvShows = popularTvShowsState.list
                            )
                        }
                    }

                    BottomMenu(
                        item = listOf(
                            BottomMenuContent("Home" , R.drawable.house_solid),
                            BottomMenuContent("Coming Soon" , R.drawable.circle_play_regular),
                            BottomMenuContent("Downloads" , R.drawable.download_solid)
                        ),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(85.dp)

                    )

                }






        }


    }
}

@Composable
fun MovieScreen(
    popularMovies: List<MovieCategory>,
    onlyOnNetflix: List<MovieCategory>,
    blockbusterMovies: List<MovieCategory>,
    popularTvShows: List<MovieCategory>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        DifferentSections(showText = "Popular Movies", movies = popularMovies)
        DifferentSections(showText = "Blockbuster Movies", movies = blockbusterMovies)
        DifferentSections(showText = "Only on Netflix", movies = onlyOnNetflix)
        DifferentSections(showText = "Popular TV Shows", movies = popularTvShows)
    }
}
@Composable
fun BottomMenu(
    item : List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighlightColor : Color = Color.Red,
    activeTextColor : Color = Color.White,
    inactiveTextColor : Color = Color.White,
    initialSelectedItemIndex : Int = 0
) {
    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(15.dp)
    ) {

        item.forEachIndexed { index, item ->

            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor
            ) {

                selectedItemIndex = index

            }

        }


    }

}

@Composable
fun BottomMenuItem(
    item : BottomMenuContent,
    isSelected : Boolean = false,
    activeHighlightColor : Color = Color.Red,
    activeTextColor : Color = Color.White,
    inactiveTextColor : Color = Color.White,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            onItemClick()
        }
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (isSelected) activeHighlightColor
                    else Color.Transparent
                )
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = item.iconId ) ,
                contentDescription = item.title,
                tint = if (isSelected) activeTextColor
                else inactiveTextColor,
                modifier = Modifier.size(20.dp)

            )
        }
        Text(
            text = item.title,
            color = if(isSelected) activeTextColor
            else inactiveTextColor
        )

    }
}

@Composable
fun TopUi() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(15.dp))
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp)
        )
    {
        Box(modifier = Modifier
            .height(550.dp)
            .width(750.dp))
        {
            
            Image(painter = painterResource(id = R.drawable.plane_moviejpg ),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
                contentAlignment = Alignment.TopStart
            )
            {

                Column {

                    Row(
                        modifier = Modifier
                            .background(color = Color.Transparent)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.netflix_symbol),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .padding(5.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(28.dp)
                                    .padding(end = 12.dp)
                            )
                        }

                    }

                    Row(
                        modifier = Modifier
                            .background(color = Color.Transparent)
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "TV Shows",
                            fontSize = 20.sp,
                            color = Color.White,
                        )
                        Text(
                            text = "Movies",
                            color = Color.White,
                            fontSize = 20.sp,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Categories",
                                fontSize = 20.sp,
                                color = Color.White,
                            )
                            Image(
                                painter = painterResource(id = R.drawable.drop_down), contentDescription = null,
                                modifier = Modifier
                                    .size(17.dp)
                                    .padding(start = 6.dp)
                            )
                        }


                    }

                }

               Box(modifier = Modifier
                   .fillMaxSize()
                   .padding(10.dp),
                   contentAlignment = Alignment.BottomStart
               )
               {

                   Column() {

                       Row(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(top = 60.dp, start = 60.dp, end = 60.dp),
                           horizontalArrangement = Arrangement.Center,
                           verticalAlignment = Alignment.CenterVertically
                       ) {
                           Text(text = "Action• ", color = Color.White, fontSize = 14.sp)
                           Text(text = "Adventure• ", color = Color.White, fontSize = 14.sp)
                           Text(text = "Thriller", color = Color.White, fontSize = 14.sp)

                       }
                       Row(
                           modifier = Modifier
                               .padding(top = 40.dp)
                               .fillMaxWidth(),
                           horizontalArrangement = Arrangement.SpaceEvenly
                       ) {
                           Column(
                               horizontalAlignment = Alignment.CenterHorizontally,
                               verticalArrangement = Arrangement.SpaceBetween
                           )
                           {
                               Image(
                                   painter = painterResource(id = R.drawable.plus_solid),
                                   contentDescription = null,
                                   modifier = Modifier.size(25.dp)
                               )
                               Text(text = "My List", color = Color.White)
                           }
                           Button(onClick ={},
                               colors = ButtonDefaults.buttonColors(Color.White),
                               shape = RoundedCornerShape(4.dp)
                           ){
                               Text(text = "Play", fontSize = 18.sp,
                                   color = Color.Black)
                           }
                           Column(
                               horizontalAlignment = Alignment.CenterHorizontally,
                               verticalArrangement = Arrangement.SpaceBetween
                           )
                           {
                               Image(
                                   painter = painterResource(id = R.drawable.info),
                                   contentDescription = null,
                                   modifier = Modifier
                                       .size(25.dp)
                                       .padding(top = 4.dp, bottom = 4.dp)
                               )
                               Text(text = "Info", color = Color.White)
                           }
                       }
                   }

               }

            }

        }
    }



}


@Composable
fun DifferentSections(
    showText : String,
    movies : List<MovieCategory>
) {


    Column(
        modifier = Modifier.fillMaxSize()
    )
    {
        Text(
            text = showText, color = Color.LightGray,
            fontSize = 24.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp, start = 12.dp)
        )

        LazyRow(modifier = Modifier.padding(top = 4.dp)) {



            itemsIndexed(movies) { index, movie ->
                MoviesSection(movie = movie)

                 }
            }
        }
    }

@Composable
fun MoviesSection(
    movie: MovieCategory
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        val imageUrl = "${Constants.ImageUrl}${movie.poster_path}"
        Image(
            painter = rememberAsyncImagePainter(imageUrl), contentDescription = null,
            modifier = Modifier
                .height(180.dp)
                .width(132.dp)
        )
        Text(text = movie.title, color = Color.White)
    }
}


