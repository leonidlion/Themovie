package com.dev.leo.themovie.adapter

import android.support.v7.widget.RecyclerView

abstract class BaseRecyclerAdapter<VH : RecyclerView.ViewHolder, T> protected constructor() : RecyclerView.Adapter<VH>() {
    protected var adapterData: MutableList<T> = ArrayList()

    fun addItem(item: T) {
        adapterData.add(item)
        notifyItemInserted(adapterData.size - 1)
    }

    fun addItem(itemList: List<T>) {
        val size = adapterData.size
        adapterData.addAll(itemList)
        notifyItemRangeInserted(size, adapterData.size)
    }

    fun clearData() {
        val size = adapterData.size
        adapterData.clear()
        notifyItemRangeRemoved(0, size)
    }

    protected fun getItem(holder: VH): T = adapterData[holder.adapterPosition]

    override fun getItemCount(): Int = adapterData.size
}