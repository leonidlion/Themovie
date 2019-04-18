package com.dev.leo.themovie.di

import com.dev.leo.themovie.network.MovieApiService
import com.dev.leo.themovie.repository.MovieRepository
import com.dev.leo.themovie.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun getMovieChanges(api: MovieApiService): MovieRepository = MovieRepositoryImpl(api)
}