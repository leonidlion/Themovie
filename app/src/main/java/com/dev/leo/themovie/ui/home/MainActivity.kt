package com.dev.leo.themovie.ui.home

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import com.dev.leo.themovie.BundleKeys
import com.dev.leo.themovie.R
import com.dev.leo.themovie.adapter.MovieHomeAdapter
import com.dev.leo.themovie.databinding.ActivityMainBinding
import com.dev.leo.themovie.hideKeyboard
import com.dev.leo.themovie.network.MovieApiService
import com.dev.leo.themovie.ui.BaseActivity
import com.dev.leo.themovie.ui.detail.MovieDetailActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MovieHomeAdapter.ItemClickListener {
    @Inject lateinit var modelFactory: MainViewModelFactory
    private lateinit var adapter: MovieHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        viewModeFactory = modelFactory

        super.onCreate(savedInstanceState)

        adapter = MovieHomeAdapter(this)

        binding.viewModel = viewModel
        binding.adapter = adapter

        viewModel.moviesLiveData.observe(this, Observer {
            if (it != null) adapter.addItem(it)
        })

        viewModel.filterChangesEvent.observe(this, Observer {
            hideKeyboard()
            adapter.clearData()
        })
    }

    override fun onItemClick(movie: MovieApiService.Dto.Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(BundleKeys.KEY_MOVIE.name, movie)
        startActivity(intent)
    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

}
