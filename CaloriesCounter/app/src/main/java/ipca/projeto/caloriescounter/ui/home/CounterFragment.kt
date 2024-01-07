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
            val textViewNutritionS = rootView.findViewById<TextView>(R.id.textViewNutritionS)
            val buttonRemove = rootView.findViewById<Button>(R.id.buttonRemove)
            val fatTextS = rootView.findViewById<TextView>(R.id.fatTextS)
            val carbsTextS = rootView.findViewById<TextView>(R.id.carbsTextS)
            val sugarTextS = rootView.findViewById<TextView>(R.id.sugarTextS)
            val proteinTextS = rootView.findViewById<TextView>(R.id.proteinTextS)
            val sodiumTextS = rootView.findViewById<TextView>(R.id.sodiumTextS)
            val fiberTextS = rootView.findViewById<TextView>(R.id.fiberTextS)

            val fat = currentAlimento.fat.toString()
            val carb = currentAlimento.carb.toString()
            val sugar = currentAlimento.sugar.toString()
            val protein = currentAlimento.protein.toString()
            val sodium = currentAlimento.sodium.toString()
            val fiber = currentAlimento.fiber.toString()
            fatTextS.text = "Fat: $fat"
            carbsTextS.text = "Carbs: $carb"
            sugarTextS.text = "Sugars: $sugar"
            proteinTextS.text = "Protein: $protein"
            sodiumTextS.text = "Sodium: $sodium"
            fiberTextS.text = "Fiber: $fiber"

            textViewAlimentoS.text = currentAlimento.nome
            var cal = currentAlimento.cal.toString()
            textViewCalsS.text = "Calorias: ${cal} / 100g"
            textViewNutritionS.text = "Valores nutricionais / 100g"

            buttonRemove.setOnClickListener {
                // Here, you can remove the first Alimento from the list for demonstration purposes
                sharedAlimentoViewModel.selectedAlimentos.value?.let { selectedList ->
                    if (selectedList.isNotEmpty()) {
                        sharedAlimentoViewModel.removeSelectedAlimento(currentAlimento)
                    }
                }
            }

            val nutrition = arrayListOf<TextView>(
                fatTextS,carbsTextS,sugarTextS,proteinTextS,sodiumTextS,fiberTextS
            )

            textViewNutritionS.setOnClickListener {
                val visibility = if (fatTextS.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                nutrition.forEach { textView ->
                    textView.visibility = visibility
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