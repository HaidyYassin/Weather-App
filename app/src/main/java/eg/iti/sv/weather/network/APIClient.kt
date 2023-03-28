package eg.iti.sv.weather.network

import eg.iti.sv.weather.models.WeatherResponse

class APIClient private constructor() : RemoteSource{
    private val retrofitService: API_Service by lazy {
        RetrofitHelper.getInstance().create(API_Service::class.java)
    }

    companion object{
        private var instance: APIClient? = null
        fun getInstance() :APIClient{
            return instance?: synchronized(this){
                val temp = APIClient()
                instance = temp
                temp
            }
        }
    }

    override suspend fun getWeatherOverNetwork(): WeatherResponse {
        val root = retrofitService.getWeather()
        println(root.body())
        return root.body()!!
    }
}