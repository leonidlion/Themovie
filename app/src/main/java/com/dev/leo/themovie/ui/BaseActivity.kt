package com.dev.leo.themovie.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

abstract class BaseActivity<B: ViewDataBinding, VM: ViewModel>: AppCompatActivity() {
    protected lateinit var viewModel: VM
    protected lateinit var binding: B
    protected var viewModeFactory: ViewModelProvider.Factory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutRes())

        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this, viewModeFactory).get(getViewModelClass())
    }

    protected abstract fun getLayoutRes(): Int

    protected abstract fun getViewModelClass(): Class<VM>

    protected fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    protected fun showToast(@StringRes message: Int) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}