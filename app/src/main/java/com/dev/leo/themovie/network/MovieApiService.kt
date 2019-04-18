package com.dev.leo.themovie.network

import android.arch.lifecycle.LiveData
import com.google.gson.annotations.SerializedName
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.Serializable

interface MovieApiService {
    @GET("movie/changes")
    fun getMovieChanges(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String?,
        @Query("end_date") endDate: String?): LiveData<ApiResponse<Dto.Changes>>

    @GET("movie/{id}")
    fun getDetailMovie(@Path("id") id: Long,
                       @Query("api_key") apiKey: String,
                       @Query("append_to_response") includeField: String): LiveData<ApiResponse<Dto.Movie>>

    @GET("movie/changes")
    fun getRxMovieChanges(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String?,
        @Query("end_date") endDate: String?): Flowable<Dto.Changes>

    @GET("movie/{id}")
    fun getRxDetailMovie(@Path("id") id: Long,
                       @Query("api_key") apiKey: String,
                       @Query("append_to_response") includeField: String): Single<Dto.Movie>

    sealed class Dto{
        data class Changes(
            @SerializedName("results") val results: List<ChangesMovie>,
            @SerializedName("page") val page: Int,
            @SerializedName("total_pages") val totalPages: Int,
            @SerializedName("total_results") val totalResults: Long
        ): Serializable

        data class ChangesMovie(
            @SerializedName("id") val id: Long,
            @SerializedName("adult") val adult: Boolean
        ): Serializable

        data class Movie(
            @SerializedName("id") val id: Long,
            @SerializedName("title") val title: String,
            @SerializedName("spoken_languages") val language: List<SpokenLanguage>,
            @SerializedName("genres") val genres: List<Genres>,
            @SerializedName("overview") val overview: String,
            @SerializedName("release_date") val releaseDate: String,
            @SerializedName("homepage") val homePage: String,
            @SerializedName("poster_path") val poster: String,
            @SerializedName("videos") val videos: Results<Video>
        ): Serializable

        data class Genres(
            @SerializedName("id") val id: Int,
            @SerializedName("name") val name: String
        ): Serializable

        data class SpokenLanguage(
            @SerializedName("iso_639_1") val iso: String,
            @SerializedName("name") val name: String
        ): Serializable

        data class Video(
            @SerializedName("id") val id: String,
            @SerializedName("key") val key: String,
            @SerializedName("name") val name: String,
            @SerializedName("site") val site: String,
            @SerializedName("type") val type: String
        ): Serializable

        data class Results<T>(
            @SerializedName("results") val results: List<T>
        ): Serializable
    }
}