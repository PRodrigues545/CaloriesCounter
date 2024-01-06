package ipca.projeto.caloriescounter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.ActivityNavigatorExtras
import ipca.projeto.caloriescounter.databinding.FragmentCounterBinding

class CounterFragment : Fragment(){

    private var _binding: FragmentCounterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCounterBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


}