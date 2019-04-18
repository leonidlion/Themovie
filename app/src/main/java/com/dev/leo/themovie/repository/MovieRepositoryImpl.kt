package com.dev.leo.themovie.repository

import android.arch.lifecycle.LiveData
import com.dev.leo.themovie.BuildConfig
import com.dev.leo.themovie.network.ApiResponse
import com.dev.leo.themovie.network.MovieApiService
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(val api: MovieApiService): MovieRepository {
    override fun getRxChanges(startDate: String?, endDate: String?): Flowable<MovieApiService.Dto.Changes> =
        api.getRxMovieChanges(BuildConfig.API_KEY, startDate, endDate)


    override fun getRxMovie(id: Long): Single<MovieApiService.Dto.Movie> =
        api.getRxDetailMovie(id, BuildConfig.API_KEY, "videos")


    override fun getChanges(
        startDate: String?,
        endDate: String?
    ): LiveData<ApiResponse<MovieApiService.Dto.Changes>> =
        api.getMovieChanges(BuildConfig.API_KEY, startDate, endDate)


    override fun getMovie(id: Long): LiveData<ApiResponse<MovieApiService.Dto.Movie>> =
        api.getDetailMovie(id, BuildConfig.API_KEY, "videos")
}