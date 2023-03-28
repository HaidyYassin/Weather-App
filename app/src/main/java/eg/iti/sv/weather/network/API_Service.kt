package eg.iti.sv.weather.network

import eg.iti.sv.weather.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface API_Service {
    @GET("onecall")
    suspend fun getWeather(@Query("lat") lat: String? ="33.44" ,
                           @Query("lon") lon: String? ="-94",
                           @Query("exclude") exclude: String?="minutely",
                           @Query("units") units: String?="metric",
                           @Query("appid") appid: String?="f2f9ec409c67b8498f33c2bf4c7fb7e7"): Response<WeatherResponse>

}