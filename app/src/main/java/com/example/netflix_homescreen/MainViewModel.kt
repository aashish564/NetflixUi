package com.example.netflix_homescreen

import androidx.compose.material3.Text
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MainViewModel : ViewModel() {

    private val _popularMovies = MutableStateFlow(MovieState())
    val popularMovies: StateFlow<MovieState> = _popularMovies

    private val _onlyOnNetflix = MutableStateFlow(MovieState())
    val onlyOnNetflix: StateFlow<MovieState> = _onlyOnNetflix

    private val _blockbusterMovies = MutableStateFlow(MovieState())
    val blockbusterMovies: StateFlow<MovieState> = _blockbusterMovies

    private val _popularTvShows = MutableStateFlow(MovieState())
    val popularTvShows: StateFlow<MovieState> = _popularTvShows

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            fetchPopularMovies()
            fetchOnlyOnNetflix()
            fetchBlockbusterMovies()
            fetchPopularTvShows()
        }
    }

    private fun MovieModel.toMovieCategory(): MovieCategory {
        return MovieCategory(
            id = this.id.toInt(),
            title = this.title,
            poster_path = this.poster,
            description = this.description
        )
    }

    private suspend fun fetchPopularMovies() {
        try {
            val response = movieService.getPopularMovies(Constants.API_KEY)
            _popularMovies.value = MovieState(
                list = response.body()?.results?.map { it.toMovieCategory() } ?: emptyList(),
                loading = false,
                error = null
            )
        } catch (e: Exception) {
            _popularMovies.value = MovieState(
                list = emptyList(),
                loading = false,
                error = e.message
            )
        }
    }

    private suspend fun fetchOnlyOnNetflix() {
        try {
            val response = movieService.getOnlyOnNetflix(Constants.API_KEY)
            _onlyOnNetflix.value = MovieState(
                list = response.body()?.results?.map { it.toMovieCategory() } ?: emptyList(),
                loading = false,
                error = null
            )
        } catch (e: Exception) {
            _onlyOnNetflix.value = MovieState(
                list = emptyList(),
                loading = false,
                error = e.message
            )
        }
    }

    private suspend fun fetchBlockbusterMovies() {
        try {
            val response = movieService.getBlockbusterMovies(Constants.API_KEY)
            _blockbusterMovies.value = MovieState(
                list = response.body()?.results?.map { it.toMovieCategory() } ?: emptyList(),
                loading = false,
                error = null
            )
        } catch (e: Exception) {
            _blockbusterMovies.value = MovieState(
                list = emptyList(),
                loading = false,
                error = e.message
            )
        }
    }

    private suspend fun fetchPopularTvShows() {
        try {
            val response = movieService.getPopularTvShows(Constants.API_KEY)
            _popularTvShows.value = MovieState(
                list = response.body()?.results?.map { it.toMovieCategory() } ?: emptyList(),
                loading = false,
                error = null
            )
        } catch (e: Exception) {
            _popularTvShows.value = MovieState(
                list = emptyList(),
                loading = false,
                error = e.message
            )
        }
    }

    data class MovieState(
        val loading: Boolean = true,
        val list: List<MovieCategory> = emptyList(),
        val error: String? = null
    )
}

//    private var _movielist = MutableLiveData<List<MovieModel>>()
//    val movieList: LiveData<List<MovieModel>> = _movielist

//    private val _popularMovies = MutableStateFlow(MovieState())
//    val popularMovies: StateFlow<MovieState> = _popularMovies
//
//    private val _onlyOnNetflix = MutableStateFlow(MovieState())
//    val onlyOnNetflix: StateFlow<MovieState> = _onlyOnNetflix
//
//    private val _blockbusterMovies = MutableStateFlow(MovieState())
//    val blockbusterMovies: StateFlow<MovieState> = _blockbusterMovies
//
//    private val _popularTvShows = MutableStateFlow(MovieState())
//    val popularTvShows: StateFlow<MovieState> = _popularTvShows

//    init {
//
//        //fetchCategories()
//
//
//
//
//    }

//    private fun fetchCategories() {
//        viewModelScope.launch {
//
//            try {
//
//                val response = movieService.getPopularMovies(Constants.API_KEY)
//                _popularMovies.value = _popularMovies.value.copy(
//                    list = response.result,
//                    loading = false,
//                    error = null
//                )
//
//                val netflixresponse = movieService.getOnlyOnNetflix(Constants.API_KEY)
//                _onlyOnNetflix.value = _onlyOnNetflix.value.copy(
//                    list = netflixresponse.result,
//                    loading = false,
//                    error = null
//                )
//
//                val blockbusterresponse = movieService.getBlockbusterMovies(Constants.API_KEY)
//                _blockbusterMovies.value = _blockbusterMovies.value.copy(
//                    list = blockbusterresponse.result,
//                    loading = false,
//                    error = null
//                )
//
//                val tvshowresponse = movieService.getPopularTvShows(Constants.API_KEY)
//                _popularTvShows.value = _popularTvShows.value.copy(
//                    list = tvshowresponse.result,
//                    loading = false,
//                    error = null
//                )
//
//            } catch (e: Exception) {
//
//                println("HTTP error: ${e.message}")
//
//            }
//
//
//        }
//    }

//    fun getPopularMovies() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = movieService.getPopularMovies(Constants.API_KEY)
//            withContext(Dispatchers.Main) {
//                _movielist.value = response.body()!!.results.sortedByDescending {it.votes}
//            }
//        }
//    }
//
//    fun getOnlyOnNetflix() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = movieService.getOnlyOnNetflix(Constants.API_KEY)
//            withContext(Dispatchers.Main) {
//                _movielist.value = response.body()!!.results.sortedByDescending {it.votes}
//            }
//        }
//    }
//
//    fun getBlockbusterMovies() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = movieService.getBlockbusterMovies(Constants.API_KEY)
//            withContext(Dispatchers.Main) {
//                _movielist.value = response.body()!!.results.sortedByDescending {it.votes}
//            }
//        }
//    }
//
//    fun getPopularTvShows() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = movieService.getPopularTvShows(Constants.API_KEY)
//            withContext(Dispatchers.Main) {
//                _movielist.value = response.body()!!.results.sortedByDescending {it.votes}
//            }
//        }
//    }

//    data class MovieState(
//        val loading : Boolean = true,
//        val list : List<MovieCategory> = emptyList(),
//        val error : String? = null
//    )

