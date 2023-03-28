package eg.iti.sv.weather.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.iti.sv.weather.databinding.HourWeatherCardBinding
import eg.iti.sv.weather.models.Constants
import eg.iti.sv.weather.models.Hourly
import eg.iti.sv.weather.utils.getHourString

class HourWeatherAdapter(private val context: Context, private var hourly: List<Hourly>): RecyclerView.Adapter<HourWeatherAdapter.ViewHolder>(){
    private lateinit var binding: HourWeatherCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = HourWeatherCardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    fun setList(myhourly: List<Hourly>) {
        hourly = myhourly
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = hourly.get(position)
        binding.hourTimeTxt.text = getHourString(currentItem.dt)
        binding.hourTempTxt.text = currentItem.temp.toString()
        Glide
            .with(context)
            .load(Constants.WEATHER_IMAGE_BASE_URL+currentItem.weather.get(0).icon+".png")
            .into(binding.hourIconTxt)


    }

    override fun getItemCount(): Int = hourly.size

    inner class ViewHolder(binding: HourWeatherCardBinding) : RecyclerView.ViewHolder(binding.root) {}

}
