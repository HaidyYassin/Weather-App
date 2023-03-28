package eg.iti.sv.weather.fav.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import eg.iti.sv.weather.R
import eg.iti.sv.weather.databinding.FragmentMapBinding
import eg.iti.sv.weather.db.ConcreteLocalSource
import eg.iti.sv.weather.models.FavPlace
import eg.iti.sv.weather.models.Repository
import eg.iti.sv.weather.models.RepositoryInterface
import eg.iti.sv.weather.network.APIClient

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    lateinit var geocoder:Geocoder
     var place =""
    private lateinit var favPlace: FavPlace

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        geocoder = Geocoder(activity?.applicationContext as Context)

        binding = FragmentMapBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val supportMapFragment : SupportMapFragment = childFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        supportMapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                googleMap.setOnMapClickListener {
                    val marker = MarkerOptions()
                    marker.position(it)
                    place = getCityName(it.longitude,it.latitude)
                    marker.title(place)
                    favPlace = FavPlace(paceName = place, longitude = it.longitude, latitude = it.latitude)
                    googleMap.clear()
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it,5f))
                    googleMap.addMarker(marker)
                    Dialog(favPlace).show(activity?.supportFragmentManager as FragmentManager,"dialog")

                }
            }

        } )
    }

    fun getCityName(longitude:Double,altitude:Double):String{

        println("--------------------------")
        println(longitude.toString()+"  "+altitude.toString())

        // val theAddress = geocoder.getFromLocation(altitude as Double, longitude as Double,5)
        val theAddress = geocoder.getFromLocation(altitude as Double, longitude as Double,5)
        if(theAddress?.size!! > 0)
        {
            return theAddress?.get(0)?.adminArea.toString()
        }
        return ""
    }


    class Dialog(val place:FavPlace): DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {
            return activity?.let {
                // Use the Builder class for convenient dialog construction
                val builder = AlertDialog.Builder(it)
                builder.setMessage("Save ${place.paceName} to Fav List?")
                    .setPositiveButton("Save",
                        DialogInterface.OnClickListener { dialog, id ->
                            Toast.makeText(requireContext(),"yes pressed",Toast.LENGTH_SHORT).show()
                        })
                    .setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, id ->

                            Toast.makeText(requireContext(),"cancel pressed",Toast.LENGTH_SHORT).show()
                        })
                // Create the AlertDialog object and return it
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }


}