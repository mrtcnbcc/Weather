package com.mertcanbicici.weather.service

import com.mertcanbicici.weather.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeAPIService {
    private val api = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeAPI::class.java)

    fun getData(city: String): Single<WeatherModel> {
        return api.getWeather(city)
    }
}