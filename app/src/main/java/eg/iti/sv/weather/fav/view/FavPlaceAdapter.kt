package eg.iti.sv.weather.fav.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eg.iti.sv.weather.databinding.FavPlaceCardBinding
import eg.iti.sv.weather.models.FavPlace

class FavPlaceAdapter(private val context: Context, private var places: List<FavPlace>, private var myListener:OnFavClickListener): RecyclerView.Adapter<FavPlaceAdapter.ViewHolder>() {
    private lateinit var binding: FavPlaceCardBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
       binding = FavPlaceCardBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.favPlaceTxt.text = places.get(position).paceName
        holder.binding.deleteBtn.setOnClickListener {
            myListener.removeFromFav(places.get(position))
        }
        holder.binding.favPlaceCardView.setOnClickListener {
          myListener.getFavWeather(places.get(position),holder.binding.root)
        }
    }

    inner class ViewHolder(var binding: FavPlaceCardBinding) : RecyclerView.ViewHolder(binding.root){}
}
