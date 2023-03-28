package eg.iti.sv.weather.fav.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import eg.iti.sv.weather.R
import eg.iti.sv.weather.databinding.FragmentFavBinding


class FavFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val size = 0

        if(size == 0){
            binding.animationViewFav.visibility = View.VISIBLE
        }else
            binding.animationViewFav.visibility = View.INVISIBLE

        binding.addFavBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_favFragment_to_mapFragment)
        }
    }

}