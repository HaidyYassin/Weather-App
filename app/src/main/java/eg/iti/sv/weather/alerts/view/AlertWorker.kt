package com.example.wheatherforcast.alerts.worker

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import eg.iti.sv.weather.alerts.view.AlarmActivity
import eg.iti.sv.weather.alerts.view.AlertsFragment
import eg.iti.sv.weather.alerts.viewmodel.AlertsViewModel
import eg.iti.sv.weather.alerts.viewmodel.AlertsViewModelFactory
import eg.iti.sv.weather.db.ConcreteLocalSource
import eg.iti.sv.weather.home.viewmodel.HomeViewModel
import eg.iti.sv.weather.home.viewmodel.HomeViewModelFactory
import eg.iti.sv.weather.models.Repository
import eg.iti.sv.weather.network.APIClient
import eg.iti.sv.weather.utils.NotificationHelper
import eg.iti.sv.weather.utils.getAppLocationByMap
import kotlinx.coroutines.*
import java.util.*


class AlertWorker(val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    private lateinit var  alertMsg:String
    private lateinit var  msg:String

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doWork(): Result {

        Log.i("input data",inputData.getString("typeAlert").toString() )

        if (inputData.getString("typeAlert").toString().equals("notification")) {

            //var city= sharedPreference.getString(Constants.cityName,"").toString()


           /* var contentNotification=(Constants.response?.current?.weather?.get(0)?.description)+"  "+approximateTemp(
                (Constants.response?.current!!.temp)!!.minus(273.15))+"\u00B0"*/


                if(AlertsFragment.weather.alerts?.get(0)?.description.toString()=="null")
                       alertMsg ="No Alerts Right Now"
                else
                    alertMsg=AlertsFragment.weather.alerts?.get(0)?.description.toString()

            NotificationHelper(context).createNotification(
                title = AlertsFragment.weather.current.weather.get(0).description.toString(),
               content =alertMsg ,
            )


            Log.i("fire noti", "notti ")
        } else {
            fireAlarmDialog()
            Log.i("fire alarm", "Alaarmm ")
        }

        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fireAlarmDialog() {
        if (Settings.canDrawOverlays(context)) {
            val intent = Intent(context, AlarmActivity::class.java)
           // intent.putExtra("msg",msg)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK /*or Intent.FLAG_INCLUDE_STOPPED_PACKAGES or Intent.FLAG_ACTIVITY_CLEAR_TASK*/)
            context.startActivity(intent)

        }
    }

}