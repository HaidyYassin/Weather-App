package eg.iti.sv.weather.map.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import eg.iti.sv.weather.R
import eg.iti.sv.weather.databinding.FragmentMapBinding
import eg.iti.sv.weather.db.ConcreteLocalSource
import eg.iti.sv.weather.map.viewmodel.MapViewModel
import eg.iti.sv.weather.map.viewmodel.MapViewModelFactory
import eg.iti.sv.weather.models.FavPlace
import eg.iti.sv.weather.models.Repository
import eg.iti.sv.weather.network.APIClient
import eg.iti.sv.weather.utils.setAppLocationByMap
import java.io.IOException


lateinit var viewModel: MapViewModel
private lateinit var myView: View
lateinit var type:String

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var geocoder:Geocoder
    private lateinit var viewModelFactory: MapViewModelFactory
    var place =""
    private lateinit var favPlace: FavPlace
    private lateinit var mMap:GoogleMap


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
       myView = view
         type = arguments?.get("fav") as String  //arguments?.getSerializable("weather",ApiState::class.java)
        //arguments?.getSerializable("weather",ApiState::class.java)

        viewModelFactory = MapViewModelFactory(
            Repository.getInstance(
                APIClient.getInstance(), ConcreteLocalSource(activity?.applicationContext as Context)
            ))

        binding.searchPlaceTxt.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                ){

                    //execute our method for searching
                    geoLocate(binding.searchPlaceTxt.text.toString())
                }

                return false;
            }


        })


        viewModel  = ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)

        val supportMapFragment : SupportMapFragment = childFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        supportMapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                mMap = googleMap
                googleMap.setOnMapClickListener {
                    val marker = MarkerOptions()
                    marker.position(it)
                    place = getCityName(it.longitude,it.latitude)
                    marker.title(place)
                    favPlace = FavPlace(paceName = place, longitude = it.longitude, latitude = it.latitude, latLog = it.latitude.toString().plus(it.longitude.toString()))
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
            return theAddress.get(0)?.adminArea.toString()
        }
        return ""
    }

    private fun geoLocate(searchString: String) {
        Log.d(TAG, "geoLocate: geolocating")
        val geocoder = Geocoder(requireContext())
        var list: List<Address>? = ArrayList()
        try {
            list = geocoder.getFromLocationName(searchString, 1)
        } catch (e: IOException) {
            Log.e(TAG, "geoLocate: IOException: " + e.message)
        }
        if (list!!.size > 0) {
            val address: Address = list[0]
            Log.d(TAG, "geoLocate: found a location: " + address.toString())

            moveCamera(
                LatLng(address.latitude, address.longitude), 5f,
                address.adminArea
            )
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float, title: String) {
        Log.d(
            TAG,
            "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
        if (title != "My Location") {
            mMap.clear()
            val options = MarkerOptions()
                .position(latLng)
                .title(title)
            mMap.addMarker(options)
            favPlace = FavPlace(paceName = title, longitude = latLng.longitude, latitude = latLng.latitude, latLog = latLng.toString())
            Dialog(favPlace).show(activity?.supportFragmentManager as FragmentManager,"dialog")
        }
        hideSoftKeyboard()
    }

    private fun hideSoftKeyboard() {
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    class Dialog(private val place:FavPlace): DialogFragment() {


        override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {
            return activity?.let {

                val builder = AlertDialog.Builder(it)
                builder.setMessage("Save ${place.paceName} ?")
                    .setPositiveButton("Save",
                        DialogInterface.OnClickListener { dialog, id ->
                            Toast.makeText(requireContext(),"Saved Successfully",Toast.LENGTH_SHORT).show()
                            if(type  == "fav")
                                viewModel.addPlaceToFav(place)
                            else
                                setAppLocationByMap(requireContext(),place.longitude.toString(),place.latitude.toString())

                        })
                    .setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, id ->
                            Toast.makeText(requireContext(),"cancel",Toast.LENGTH_SHORT).show()

                        })

                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }
}




