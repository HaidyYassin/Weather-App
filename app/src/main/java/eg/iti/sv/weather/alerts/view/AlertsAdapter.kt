package eg.iti.sv.weather.alerts.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eg.iti.sv.weather.databinding.AlertCardBinding
import eg.iti.sv.weather.databinding.FavPlaceCardBinding
import eg.iti.sv.weather.models.AlertDetails


class AlertsAdapter (private val context: Context, private var alerts: List<AlertDetails>, private var myListener: OnAlertClickListener): RecyclerView.Adapter<AlertsAdapter.ViewHolder>() {

    private lateinit var binding: AlertCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = AlertCardBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return alerts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.placeTxt.text = alerts.get(position).date
        holder.binding.fromTxt.text =  alerts.get(position).startTime
        holder.binding.toTxt.text =  alerts.get(position).endTime
        holder.binding.deleteAlertBtn.setOnClickListener {
            myListener.removeFromAlerts(alerts.get(position))
        }
    }

    inner class ViewHolder(var binding: AlertCardBinding) : RecyclerView.ViewHolder(binding.root){}

}
