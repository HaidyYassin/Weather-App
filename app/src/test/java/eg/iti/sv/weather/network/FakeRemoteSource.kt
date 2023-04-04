package eg.iti.sv.weather.network

import eg.iti.sv.weather.models.WeatherResponse

class FakeRemoteSource(private val response: WeatherResponse?): RemoteSource {
    override suspend fun getWeatherOverNetwork(
        lat: String?,
        lon: String?,
        exclude: String?,
        units: String?,
        lang: String?,
        appid: String?
    ): WeatherResponse {
        return response ?: throw NullPointerException("No response for test")
    }

}