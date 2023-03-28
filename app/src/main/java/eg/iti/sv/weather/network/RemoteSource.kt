package eg.iti.sv.weather.network

import eg.iti.sv.weather.models.WeatherResponse

interface RemoteSource {
   suspend fun getWeatherOverNetwork() : WeatherResponse
}