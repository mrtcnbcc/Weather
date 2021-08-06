package com.mertcanbicici.weather.items


import com.mertcanbicici.weather.model.WeatherModel
import java.text.SimpleDateFormat
import java.util.*

class WeatherItem(model: WeatherModel) {

    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
    var currentDate = sdf.format(Date())


    val cityName = model.name.toString()
    val sunrise = SimpleDateFormat("hh:mm", Locale.ENGLISH).format((model.sys.sunrise + model.timezone)*1000)
    val sunset = SimpleDateFormat("HH:mm", Locale.ENGLISH).format((model.sys.sunset + model.timezone)*1000)
    val tempMin = "Min " + model.main.tempMin.toInt().toString()+ "°C"
    val tempMax = "Max " + model.main.tempMax.toInt().toString()+ "°C"
    val pressure = model.main.pressure.toInt().toString()
    val degree = model.main.temp.toInt().toString() + "°C"
    val humidity = model.main.humidity.toString() + "%"
    val wind = model.wind.speed.toString() + " "+"Mph"
    val updatedAt = "Updated at: " + currentDate

}