package com.example.netflix_homescreen

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MovieResponse(
    @SerializedName("results")
    val results: List<MovieModel>
)
@Parcelize
data class MovieCategory(
    val id : Int,
    val title : String,
    val poster_path: String,
    val description : String
) : Parcelable

