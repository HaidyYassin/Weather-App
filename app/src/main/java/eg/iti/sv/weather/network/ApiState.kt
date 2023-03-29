package eg.iti.sv.weather.network

import eg.iti.sv.weather.models.WeatherResponse

sealed class ApiState : java.io.Serializable{
    class Success (val data: WeatherResponse): ApiState()
    class Failure (val msg: Throwable): ApiState()
    object Loading: ApiState()
}
