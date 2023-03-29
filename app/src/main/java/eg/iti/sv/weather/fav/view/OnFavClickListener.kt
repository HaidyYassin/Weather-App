package eg.iti.sv.weather.fav.view

import eg.iti.sv.weather.models.FavPlace

interface OnFavClickListener {
    fun removeFromFav(favPlace: FavPlace)
    fun getFavWeather()
}