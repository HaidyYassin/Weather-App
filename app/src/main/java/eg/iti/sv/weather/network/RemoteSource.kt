package eg.iti.sv.weather.network

import eg.iti.sv.weather.models.WeatherResponse
import retrofit2.http.Query

interface RemoteSource {
   suspend fun getWeatherOverNetwork(lat: String? ,
                                     lon: String?,
                                     exclude: String?="minutely",
                                     units: String?="metric",
                                     appid: String?="f2f9ec409c67b8498f33c2bf4c7fb7e7") : WeatherResponse
}