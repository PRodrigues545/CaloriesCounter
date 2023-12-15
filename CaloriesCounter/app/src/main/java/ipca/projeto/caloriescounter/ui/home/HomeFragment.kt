package ipca.projeto.caloriescounter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipca.projeto.caloriescounter.Alimento
import ipca.projeto.caloriescounter.CustomAdapter
import ipca.projeto.caloriescounter.R
import ipca.projeto.caloriescounter.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var customAdapter: CustomAdapter
    private lateinit var alimentoList: List<Alimento> // Replace Alimento with your actual data type

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)

        // Set Layout Manager (Optional)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize your data list (alimentoList)
        var alimentoList = arrayListOf<Alimento>(
            Alimento("Banana", 12.0, 12.0, 32.0, 16.5, 86.4, 87.23, 5.21, ""),
            Alimento("Batata", 12.0, 12.0, 32.0, 16.5, 86.4, 87.23, 5.21, "")
        )

        // Initialize and set the adapter
        customAdapter = CustomAdapter(alimentoList)
        recyclerView.adapter = customAdapter
    }
}
