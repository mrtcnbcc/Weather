package com.mertcanbicici.weather.service

import com.mertcanbicici.weather.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeAPI {
    @GET("data/2.5/weather?&units=metric&APPID=2bdf717136609745b0409fdae238ddc2")
    fun getWeather(
        @Query("q") city: String): Single<WeatherModel>
}