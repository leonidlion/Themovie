package com.dev.leo.themovie.ui.detail

import android.databinding.BindingAdapter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.dev.leo.themovie.BuildConfig
import com.dev.leo.themovie.BundleKeys
import com.dev.leo.themovie.GlideApp
import com.dev.leo.themovie.R
import com.dev.leo.themovie.databinding.ActivityMovieDetailBinding
import com.dev.leo.themovie.network.MovieApiService
import com.dev.leo.themovie.ui.BaseActivity

class MovieDetailActivity: BaseActivity<ActivityMovieDetailBinding, MovieDetailViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.hasExtra(BundleKeys.KEY_MOVIE.name))
            initData(intent.getSerializableExtra(BundleKeys.KEY_MOVIE.name) as MovieApiService.Dto.Movie)
    }

    private fun initData(movie: MovieApiService.Dto.Movie) {
        binding.movie = movie

        supportActionBar?.title = movie.title
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutRes(): Int = R.layout.activity_movie_detail

    override fun getViewModelClass(): Class<MovieDetailViewModel> = MovieDetailViewModel::class.java

    companion object {
        @JvmStatic
        @BindingAdapter("app:imageUrlPoster")
        fun setImage(poster: ImageView, url: String?) {
            val imageUrl: String? = if (url.isNullOrEmpty()) null else BuildConfig.IMAGE_URL + url
            GlideApp.with(poster.context)
                .load(imageUrl)
                .error(R.drawable.ic_launcher_foreground)
                .into(poster)
        }

        @JvmStatic
        @BindingAdapter("app:showLanguage")
        fun setLanguage(textView: TextView, languages: List<MovieApiService.Dto.SpokenLanguage>?){
            if (languages.isNullOrEmpty()) textView.visibility = View.GONE
            else {
                val names = languages.map { it.name }
                textView.text = names.toString()
            }
        }
        @JvmStatic
        @BindingAdapter("app:showGender")
        fun setGenders(textView: TextView, genders: List<MovieApiService.Dto.Genres>?){
            if (genders.isNullOrEmpty()) textView.visibility = View.GONE
            else {
                val names = genders.map { it.name }
                textView.text = names.toString()
            }
        }
    }
}