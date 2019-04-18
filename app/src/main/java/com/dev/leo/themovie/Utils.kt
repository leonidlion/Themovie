package com.dev.leo.themovie

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.dev.leo.themovie.network.*
import com.dev.leo.themovie.ui.home.DateInterval
import java.text.SimpleDateFormat
import java.util.*

fun convertIntervalToDateInterval(interval: Int): DateInterval{
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val endDate = simpleDateFormat.format(calendar.time)
    calendar.set(Calendar.DAY_OF_MONTH, day - interval)
    val startDate = simpleDateFormat.format(calendar.time)

    return DateInterval(startDate, endDate)
}

fun <T, X> switchMapForApiResponse(liveData: LiveData<ApiResponse<T>>, doOnSubscribe: (() -> Unit)? = null, doOnSuccess: (((T?) -> X?)?) = null,
                                   doOnError: (((Throwable) -> Unit)?) = null): LiveData<X?>? {
    val response = Transformations.map(liveData) {
        when (it) {
            is ApiIsLoading -> {
                doOnSubscribe?.invoke()
                null
            }
            is ApiSuccessResponse -> {
                val responseBody = it.body
                doOnSuccess?.invoke(responseBody)
            }
            is ApiEmptyResponse<*> -> {
                doOnSuccess?.invoke(null)
                null
            }
            is ApiErrorResponse<*> -> {
                doOnError?.invoke(it.errorMessage)
                null
            }
            else -> null
        }
    }
    return response
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}