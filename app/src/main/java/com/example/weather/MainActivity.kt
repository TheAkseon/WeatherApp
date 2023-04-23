package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var Input_field: EditText? = null
    private var btn: Button? = null
    private var result: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Input_field = findViewById(R.id.Input_field)
        btn = findViewById(R.id.btn)
        result = findViewById(R.id.result)

        btn?.setOnClickListener{
            if(Input_field?.text?.toString()?.trim()?.equals("")!!) {
                result?.text = "Sorry, but we cannot find such a city"
            } else if(Input_field?.text?.toString()?.trim()?.equals("credits")!!){
                result?.text = "Author: Семён Семёныч"
            } else{
                var city: String = Input_field!!.text.toString()
                var key: String = "fdc318275623afc990507832bb21883c"
                var url: String = "https://api.openweathermap.org/data/2.5/weather?q=Kiev&appid=642ee28be1d1e6fc96f9bb5fd4157b0b&units=metric&lang=ru" //

                doAsync {
                    val apiResponse = URL(url).readText()

                    Log.d("INFO", apiResponse)

                    val weather = JSONObject(apiResponse).getJSONArray("weather")
                    val ending_weather = weather.getJSONObject(0).getString("description")

                    val main = JSONObject(apiResponse).getJSONObject("main")
                    val temp = main.getString("temp")

                    val wind = JSONObject(apiResponse).getJSONObject("wind")
                    val speed = wind.getString("speed")

                    result?.text = "Температура: $temp\n$ending_weather\nСкорость ветра: $speed"
                }
            }
        }
    }
}