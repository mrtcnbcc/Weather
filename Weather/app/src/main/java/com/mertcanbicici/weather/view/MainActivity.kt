package com.mertcanbicici.weather.view

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mertcanbicici.weather.R
import com.mertcanbicici.weather.items.WeatherItem
import kotlinx.android.synthetic.main.activity_main.*
import com.mertcanbicici.weather.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        getWeather()

        var cityName = GET.getString("cityName", "istanbul")?.toLowerCase()
        edt_city_name.setText(cityName)
        viewmodel.refresh(cityName!!)



        swipe_refresh_layout.setOnRefreshListener {
            mainContainer.visibility = View.GONE
            errorMessage.visibility = View.GONE
            pb_loading.visibility = View.GONE

            var cityName = GET.getString("cityName", cityName)?.toLowerCase()
            edt_city_name.setText(cityName)
            viewmodel.refresh(cityName!!)

            swipe_refresh_layout.isRefreshing = false
    }

        img_search_city.setOnClickListener {
            val cityName = edt_city_name.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewmodel.refresh(cityName)
            getWeather()
            Log.i(TAG, "onCreate: " + cityName)
        }

        errorMessage.setOnClickListener {
            viewmodel.refresh("istanbul")
            getWeather()
            Log.i(TAG, "onCreate: " + cityName)
        }
}



    private fun getWeather() {

        viewmodel.weather_data.observe(this, Observer { data ->
            data?.let {
                mainContainer.visibility = View.VISIBLE

                val item = WeatherItem(data)

                tv_city_name.text = item.cityName
                tv_sunrise.text = item.sunrise
                tv_sunset.text = item.sunset
                temp_min.text = item.tempMin
                temp_max.text = item.tempMax
                pressure.text = item.pressure
                tv_degree.text = item.degree
                humidity.text = item.humidity
                wind.text = item.wind
                updated_at.text = item.updatedAt


            }
        })
        viewmodel.weather_error.observe(this, Observer { error ->
            error?.let {
                if (error) {

                    errorMessage.visibility = View.VISIBLE
                    pb_loading.visibility = View.GONE
                    mainContainer.visibility = View.GONE

                } else {
                    errorMessage.visibility = View.GONE
                }
            }
        })

        viewmodel.weather_loading.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    mainContainer.visibility = View.GONE
                } else {
                    pb_loading.visibility = View.GONE
                }
            }
        })

    }}




