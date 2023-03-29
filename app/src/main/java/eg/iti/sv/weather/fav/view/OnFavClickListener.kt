package eg.iti.sv.weather.fav.view

import android.view.View
import eg.iti.sv.weather.models.FavPlace

interface OnFavClickListener {
    fun removeFromFav(favPlace: FavPlace)
    fun getFavWeather(favPlace: FavPlace,view: View)
}