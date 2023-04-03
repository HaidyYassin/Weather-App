package eg.iti.sv.weather.alerts.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import eg.iti.sv.weather.R
import eg.iti.sv.weather.alerts.viewmodel.AlertsViewModel
import eg.iti.sv.weather.alerts.viewmodel.AlertsViewModelFactory
import eg.iti.sv.weather.databinding.FragmentAlertsBinding
import eg.iti.sv.weather.db.ConcreteLocalSource
import eg.iti.sv.weather.models.AlertDetails
import eg.iti.sv.weather.models.Repository
import eg.iti.sv.weather.network.APIClient
import java.text.SimpleDateFormat
import java.util.*



class AlertsFragment : Fragment(),OnAlertClickListener {

    private lateinit var binding :FragmentAlertsBinding
    private lateinit var viewModel: AlertsViewModel
    private lateinit var viewModelFactory: AlertsViewModelFactory
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: AlertsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlertsBinding.inflate(inflater,container,false)

        viewModelFactory = AlertsViewModelFactory(
            Repository.getInstance(
                APIClient.getInstance(), ConcreteLocalSource(requireContext())
                ,requireContext()))

        viewModel = ViewModelProvider(this,viewModelFactory).get(AlertsViewModel::class.java)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.alerts.observe(requireActivity()) { alerts ->
            if (alerts != null) {

                layoutManager = LinearLayoutManager(requireContext())
                layoutManager.orientation = RecyclerView.VERTICAL
                adapter = AlertsAdapter(requireContext(), alerts, this)
                binding.alertsRecycler.adapter = this.adapter
                binding.alertsRecycler.layoutManager = layoutManager
            }
        }
        binding.addAlertBtn.setOnClickListener{

            MyAlertDialog(viewModel).show(activity?.supportFragmentManager as FragmentManager,"my alert")
        }
        val size = 0
         val myView: View = view.findViewById(R.id.animation_view_alert)
        if(size == 0){
            myView.visibility = View.VISIBLE
        }else
            myView.visibility = View.INVISIBLE
    }

class MyAlertDialog(val myViewModel: AlertsViewModel) : DialogFragment() {


    private lateinit var alertDetails: AlertDetails
     private lateinit var save:Button
     private lateinit var calender:ImageView
     private lateinit var start:ImageView
     private lateinit var end:ImageView
     private lateinit var calendarTxt:TextView
     private lateinit var startTxt:TextView
     private lateinit var endTxt:TextView
     private lateinit var radioGroup: RadioGroup
     private lateinit var notificationRadio:RadioButton
     private lateinit var alarmRadio:RadioButton
     private lateinit var type:String

     override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {
         return activity?.let {
             val builder = AlertDialog.Builder(it)
             val customLayout: View = layoutInflater.inflate(R.layout.alert_dialog, null)
             builder.setView(customLayout)
             dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

             save = customLayout.findViewById(R.id.seve_alert_btn)
             calender = customLayout.findViewById(R.id.calender_btn)
             start = customLayout.findViewById(R.id.from_time_btn)
             end = customLayout.findViewById(R.id.to_time_btn)
             calendarTxt = customLayout.findViewById(R.id.alert_date_txt)
             startTxt = customLayout.findViewById(R.id.alert_from_txt)
             endTxt = customLayout.findViewById(R.id.alert_to_txt)
             radioGroup = customLayout.findViewById(R.id.alert_radio_group)
             notificationRadio = customLayout.findViewById(R.id.radio_notification)
             alarmRadio = customLayout.findViewById(R.id.radio_alarm)

             calender.setOnClickListener {
                   pickDateTime()
               }
             start.setOnClickListener {
                 pickStartTime()
             }
             end.setOnClickListener {
                 pickEndTime()
             }

             radioGroup.setOnCheckedChangeListener { _, checkedId ->
                 var alertType = customLayout.findViewById<View>(checkedId) as RadioButton

                     when (alertType) {
                         notificationRadio -> {
                             println("notification")
                             type = "notification"

                         }
                         alarmRadio -> {
                             println("alarm")
                             type = "alarm"

                         }
                     }
                 }

             save.setOnClickListener {
                    if (!checkFields())
                        Toast.makeText(
                            requireContext(),
                            "Required Empty Fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    else {
                        Toast.makeText(requireContext(), "Saved Successfully", Toast.LENGTH_SHORT)
                            .show()
                        alertDetails = AlertDetails(calendarTxt.text.toString(),
                            startTxt.text.toString(),
                            endTxt.text.toString(),
                            type,
                            pk=calendarTxt.text.toString()+startTxt.text.toString()+endTxt.text.toString()
                        )
                        println(alertDetails)
                        myViewModel.addAlert(alertDetails)
                        dialog?.dismiss()
                    }
                }

                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }


    private fun pickDateTime() {
        val currentDate: Calendar = Calendar.getInstance()
        val calender: Calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calender.set(year, month, dayOfMonth)
                calendarTxt.text = SimpleDateFormat("dd MMMM").format(calender.time).toString()

            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DATE)
        )
        datePickerDialog.datePicker.minDate = currentDate.getTimeInMillis()
        datePickerDialog.show()
    }



    private fun pickStartTime(){
        val calender: Calendar= Calendar.getInstance();
        val hour = calender.get(Calendar.HOUR_OF_DAY);
        val minute = calender.get(Calendar.MINUTE);

        val timePickerDialog =  TimePickerDialog(requireContext(),
            object :  TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    startTxt.text = hourOfDay.toString()+ ":" + minute.toString()
                }
            }, hour, minute, false);

        timePickerDialog.show();
    }

    private fun pickEndTime(){
        val calender: Calendar= Calendar.getInstance();
        val hour = calender.get(Calendar.HOUR_OF_DAY);
        val minute = calender.get(Calendar.MINUTE);

        val timePickerDialog =  TimePickerDialog(requireContext(),
            object :  TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    endTxt.text = hourOfDay.toString()+ ":" + minute.toString()
                }
            }, hour, minute, false);

        timePickerDialog.show();
    }


    private fun checkFields(): Boolean {
       return calendarTxt.text != "" && startTxt.text != "" &&
               endTxt.text !="" && (!notificationRadio.isChecked || !alarmRadio.isChecked)
    }
    }

    override fun removeFromAlerts(alertDetails: AlertDetails) {
        viewModel.removeAlert(alertDetails)
    }


}