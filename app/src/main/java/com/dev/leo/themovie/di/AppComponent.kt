package com.dev.leo.themovie.di

import com.dev.leo.themovie.MovieApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent: AndroidInjector<MovieApplication> {
    @Component.Builder
    abstract  class Builder : AndroidInjector.Builder<MovieApplication>()
}