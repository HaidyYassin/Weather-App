package eg.iti.sv.weather.models


import eg.iti.sv.weather.R

data class AppSettings(
    var location:String = "GPS",
    var lang:String="English",
    var temp:String="Celsius",
    var wind:String="meter/sec")
