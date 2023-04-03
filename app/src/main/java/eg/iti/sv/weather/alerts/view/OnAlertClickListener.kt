package eg.iti.sv.weather.alerts.view

import android.view.View
import eg.iti.sv.weather.models.AlertDetails
import eg.iti.sv.weather.models.FavPlace

interface OnAlertClickListener {
    fun removeFromAlerts(alertDetails: AlertDetails)
}