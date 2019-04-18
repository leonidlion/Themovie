package com.dev.leo.themovie.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.view.View
import com.dev.leo.themovie.SingleLiveEvent
import com.dev.leo.themovie.convertIntervalToDateInterval
import com.dev.leo.themovie.network.MovieApiService
import com.dev.leo.themovie.network.applyIoScheduler
import com.dev.leo.themovie.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(var repository: MovieRepository): ViewModel() {
    companion object{
        private val INIT_DATE_INVERVAL = convertIntervalToDateInterval(3)
        // FIXME: bug in api side https://www.themoviedb.org/talk/5c740e97c3a3685a40197326
        // so data from api return without limit and pagination functions
        private val LIMIT_DATA_COUNT = 20L
    }

    private var disposable = CompositeDisposable()

    val moviesLiveData: MutableLiveData<MovieApiService.Dto.Movie> = MutableLiveData()
    val filterChangesEvent = SingleLiveEvent<Unit>()
    val showLoader = MutableLiveData<Int>()
    val enableButton = MutableLiveData<Boolean>()

    var intervalObservableField = ObservableField<String>()

    init {
        startLoading(INIT_DATE_INVERVAL.startDate, INIT_DATE_INVERVAL.endDate)
    }

    private fun startLoading(startDate: String, endDate: String){
        disposable.add(
            repository.getRxChanges(startDate, endDate)
                .applyIoScheduler()
                .doOnSubscribe { showLoader.value = View.VISIBLE }
                .map { it.results }
                .flatMapIterable { it }
                .limit(LIMIT_DATA_COUNT)
                .filter{ !it.adult }
                .concatMap { repository.getRxMovie(it.id).applyIoScheduler().toFlowable() }
                .subscribe(
                    { moviesLiveData.value = it },
                    { showLoader.value = View.GONE },
                    { showLoader.value = View.GONE })
        )
    }


    fun onFilterTextChange(char: CharSequence, start: Int, before: Int, count: Int){
        if (char.isNotBlank()) {
            when(Integer.parseInt(char.toString())){
                in 1..14 -> enableButton.value = true
                else -> enableButton.value = false
            }
        }else enableButton.value = false
    }

    fun onFilterClick(view: View){
        filterChangesEvent.call()
        val dateInterval = convertIntervalToDateInterval(intervalObservableField.get()!!.toInt())
        startLoading(dateInterval.startDate, dateInterval.endDate)
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}