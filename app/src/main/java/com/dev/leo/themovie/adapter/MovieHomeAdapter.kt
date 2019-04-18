package com.dev.leo.themovie.adapter

import android.databinding.BindingAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.dev.leo.themovie.BuildConfig
import com.dev.leo.themovie.GlideApp
import com.dev.leo.themovie.R
import com.dev.leo.themovie.databinding.ItemMovieShortBinding
import com.dev.leo.themovie.network.MovieApiService


class MovieHomeAdapter(private val clickListener: ItemClickListener) :
    BaseRecyclerAdapter<MovieHomeAdapter.MovieHomeHolder, MovieApiService.Dto.Movie>(){
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
    }

    interface ItemClickListener{
        fun onItemClick(movie: MovieApiService.Dto.Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHomeHolder =
        MovieHomeHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie_short, parent, false))

    override fun onBindViewHolder(holder: MovieHomeHolder, position: Int) {
        holder.onBind(getItem(holder))
        holder.itemView.setOnClickListener { clickListener.onItemClick(getItem(holder)) }
    }

    class MovieHomeHolder(itemView: View) :
        BaseRecyclerHolder<ItemMovieShortBinding, MovieApiService.Dto.Movie>(itemView) {
        override fun onBind(data: MovieApiService.Dto.Movie) {
            binding?.movie = data
            binding?.executePendingBindings()
        }
    }
}