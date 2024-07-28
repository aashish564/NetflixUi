package com.example.netflix_homescreen

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("id")
    var id : String,
    @SerializedName("original_title")
    var title : String,
    @SerializedName("poster_path")
    var poster : String,
    @SerializedName("overview")
    var description : String,
    @SerializedName("vote_average")
    var votes : String
)
