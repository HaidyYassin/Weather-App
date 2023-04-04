package eg.iti.sv.weather.fav.view

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eg.iti.sv.weather.R
import eg.iti.sv.weather.databinding.FragmentFavBinding
import eg.iti.sv.weather.db.ConcreteLocalSource
import eg.iti.sv.weather.fav.viewmodel.FavViewModel
import eg.iti.sv.weather.fav.viewmodel.FavViewModelFactory
import eg.iti.sv.weather.models.FavPlace
import eg.iti.sv.weather.models.Repository
import eg.iti.sv.weather.models.Settings
import eg.iti.sv.weather.network.APIClient


class FavFragment : Fragment(),OnFavClickListener {

    private lateinit var binding:FragmentFavBinding
    private lateinit var viewModel: FavViewModel
    private lateinit var viewModelFactory: FavViewModelFactory
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: FavPlaceAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(Settings.settings.lang == "Arabic")
            Settings.setAppLocale("ar",requireContext())
        else
            Settings.setAppLocale("en",requireContext())

        binding = FragmentFavBinding.inflate(inflater,container,false)

        viewModelFactory = FavViewModelFactory(
            Repository.getInstance(
                APIClient.getInstance(), ConcreteLocalSource(requireContext())
            ))

        viewModel = ViewModelProvider(this,viewModelFactory).get(FavViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.places.observe(requireActivity()){places->
            if(places != null){
                if(places.size == 0)
                   binding.animationViewFav.visibility = View.VISIBLE
                else
                    binding.animationViewFav.visibility = View.INVISIBLE
                layoutManager = LinearLayoutManager(requireContext())
                layoutManager.orientation = RecyclerView.VERTICAL
                adapter = FavPlaceAdapter(requireContext(),places,this)
                binding.favPlacesRecycler.adapter = this.adapter
                binding.favPlacesRecycler.layoutManager = layoutManager

            }
        }

        binding.addFavBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_favFragment_to_mapFragment)
        }

    }

    override fun removeFromFav(favPlace: FavPlace) {
       viewModel.removeFromFav(favPlace)
    }

    override fun getFavWeather(favPlace: FavPlace,view:View) {
        val bundle = bundleOf("weather" to favPlace)
        Navigation.findNavController(view).navigate(R.id.action_favFragment_to_favWeatherFragment,bundle)

    }

}