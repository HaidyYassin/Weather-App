package eg.iti.sv.weather.fav.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eg.iti.sv.weather.R

class FavFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val size = 0
        val myView: View = view.findViewById(R.id.animation_view_fav)
        if(size == 0){
            myView.visibility = View.VISIBLE
        }else
            myView.visibility = View.INVISIBLE
    }

}