package com.dev.leo.themovie.di

import com.dev.leo.themovie.repository.MovieRepository
import com.dev.leo.themovie.ui.home.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    internal fun provideViewModelFactory(repository: MovieRepository): MainViewModelFactory = MainViewModelFactory(repository)
}