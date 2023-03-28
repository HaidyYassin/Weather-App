package eg.iti.sv.weather.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.iti.sv.weather.databinding.WeekWeatherCardBinding
import eg.iti.sv.weather.models.Constants
import eg.iti.sv.weather.models.Daily
import eg.iti.sv.weather.utils.getWeekDay

class WeekWeatherAdapter (private val context: Context, private var daily: List<Daily>): RecyclerView.Adapter<WeekWeatherAdapter.ViewHolder>(){
    private lateinit var binding: WeekWeatherCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = WeekWeatherCardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    fun setList(myDaily: List<Daily>) {
        daily = myDaily
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = daily.get(position)
        binding.weekDayTxt.text = getWeekDay(currentItem.dt)
        binding.weekTempTxt.text = currentItem.temp.min.toString() +" / " + currentItem.temp.max.toString()
        binding.weekTempDescTxt.text = currentItem.weather.get(0).description

        Glide
            .with(context)
            .load(Constants.WEATHER_IMAGE_BASE_URL+currentItem.weather.get(0).icon+".png")
            .into(binding.weekIconTxt)
    }

    override fun getItemCount(): Int = daily.size

    inner class ViewHolder(binding: WeekWeatherCardBinding) : RecyclerView.ViewHolder(binding.root) {}

}
