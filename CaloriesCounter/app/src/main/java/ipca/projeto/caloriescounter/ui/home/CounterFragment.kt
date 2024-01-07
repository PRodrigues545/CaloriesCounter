package ipca.projeto.caloriescounter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ipca.projeto.caloriescounter.databinding.FragmentCounterBinding
import androidx.lifecycle.Observer
import ipca.projeto.caloriescounter.Alimento
import ipca.projeto.caloriescounter.R
import ipca.projeto.caloriescounter.ui.home.HomeFragment

class CounterFragment : Fragment(){
    private val sharedAlimentoViewModel: SharedAlimentoViewModel by activityViewModels()

    private var _binding: FragmentCounterBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedAlimentosAdapter: SelectedAlimentoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCounterBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedAlimentosAdapter = SelectedAlimentoAdapter(emptyList()) // Initialize with an empty list initially

        val listViewSelectedAlimentos = binding.alimentoList
        listViewSelectedAlimentos.adapter = selectedAlimentosAdapter

        sharedAlimentoViewModel.selectedAlimentos.observe(viewLifecycleOwner, Observer { selectedAlimentos ->
            selectedAlimentosAdapter.updateSelectedAlimentos(selectedAlimentos)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class SelectedAlimentoAdapter(private var selectedAlimentos: List<Alimento>) : BaseAdapter() {
        override fun getCount(): Int {
            return selectedAlimentos.size
        }

        override fun getItem(position: Int): Any {
            return selectedAlimentos[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = convertView ?: layoutInflater.inflate(R.layout.row_selected, parent, false)

            val currentAlimento = selectedAlimentos[position]

            val imageViewS = rootView.findViewById<ImageView>(R.id.ImageViewS)
            val textViewAlimentoS = rootView.findViewById<TextView>(R.id.textViewAlimentoS)
            val textViewCalsS = rootView.findViewById<TextView>(R.id.textViewCalsS)
            val buttonRemove = rootView.findViewById<Button>(R.id.buttonRemove)

            textViewAlimentoS.text = currentAlimento.nome
            var cal = currentAlimento.cal.toString()
            textViewCalsS.text = "Calorias: ${cal} / 100g"

            buttonRemove.setOnClickListener {
                // Here, you can remove the first Alimento from the list for demonstration purposes
                sharedAlimentoViewModel.selectedAlimentos.value?.let { selectedList ->
                    if (selectedList.isNotEmpty()) {
                        sharedAlimentoViewModel.removeSelectedAlimento(currentAlimento)
                    }
                }
            }
            return rootView
        }

        fun updateSelectedAlimentos(alimentos: List<Alimento>) {
            selectedAlimentos = alimentos
            notifyDataSetChanged()
        }
    }
}