package com.dev.leo.themovie.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View


abstract class BaseRecyclerHolder<B : ViewDataBinding, T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var binding: B? = null

    init {
        binding = DataBindingUtil.bind(itemView)
    }

    abstract fun onBind(data: T)
}