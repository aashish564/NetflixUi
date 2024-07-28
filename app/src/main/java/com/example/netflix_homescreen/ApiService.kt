package com.example.netflix_homescreen

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query




    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

    val movieService =
        retrofit.create(ApiService::class.java)



interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

    @GET("discover/tv")
    suspend fun getOnlyOnNetflix(
        @Query("api_key") apiKey: String,
        @Query("with_networks") networkId: Int = 213
    ): Response<MovieResponse>

    @GET("discover/movie")
    suspend fun getBlockbusterMovies(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: String = "28"
    ): Response<MovieResponse>

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>
}