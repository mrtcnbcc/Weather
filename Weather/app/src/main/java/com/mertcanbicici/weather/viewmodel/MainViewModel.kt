package com.mertcanbicici.weather.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mertcanbicici.weather.model.WeatherModel
import com.mertcanbicici.weather.service.WeAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*


class MainViewModel: ViewModel() {
    private val weatherApiService = WeAPIService()
    private val disposable = CompositeDisposable()

    val weather_data = MutableLiveData<WeatherModel>()
    val weather_error = MutableLiveData<Boolean>()
    val weather_loading = MutableLiveData<Boolean>()

    fun refresh(city: String) {
        dataAPI(city)
    }

    private fun dataAPI(city: String) {
        weather_loading.value = true
        disposable.add(
            weatherApiService.getData(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {

                    override fun onSuccess(t: WeatherModel) {
                        weather_data.value = t
                        weather_error.value = false
                        weather_loading.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        weather_error.value = true
                        weather_loading.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )


    }

}