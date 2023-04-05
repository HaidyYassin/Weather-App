package eg.iti.sv.weather.fav.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import eg.iti.sv.weather.R
import eg.iti.sv.weather.databinding.FavPlaceCardBinding
import eg.iti.sv.weather.models.FavPlace
import eg.iti.sv.weather.utils.isNetworkAvailable

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

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder
                .setTitle("Are you sure you want to delete this Place?")
                .setMessage("This action cannot be undone")
                .setPositiveButton("OK") { dialog, which ->
                    myListener.removeFromFav(places.get(position))
                }

                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss() } // avoid problem by clicking in any place outside the dialog or back button
                .setCancelable(false)
                .show()

        }
        holder.binding.favPlaceCardView.setOnClickListener {
            if(isNetworkAvailable(context))
                myListener.getFavWeather(places.get(position),holder.binding.root)
            else
                Toast.makeText(context,"Check your connection to view weather", Toast.LENGTH_SHORT).show()

        }
    }

    inner class ViewHolder(var binding: FavPlaceCardBinding) : RecyclerView.ViewHolder(binding.root){}
}
