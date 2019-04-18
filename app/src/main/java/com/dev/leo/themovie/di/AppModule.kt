package com.dev.leo.themovie.di

import dagger.Module

@Module(includes = [NetworkModule::class, RepositoryModule::class])
class AppModule {
}