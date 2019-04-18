package com.dev.leo.themovie.repository

import android.arch.lifecycle.LiveData
import com.dev.leo.themovie.network.ApiResponse
import com.dev.leo.themovie.network.MovieApiService
import io.reactivex.Flowable
import io.reactivex.Single

interface MovieRepository {
    fun getChanges(startDate: String?, endDate: String?): LiveData<ApiResponse<MovieApiService.Dto.Changes>>
    fun getMovie(id: Long): LiveData<ApiResponse<MovieApiService.Dto.Movie>>

    fun getRxChanges(startDate: String?, endDate: String?): Flowable<MovieApiService.Dto.Changes>
    fun getRxMovie(id: Long): Single<MovieApiService.Dto.Movie>
}