package ipca.projeto.caloriescounter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Filter
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ipca.projeto.caloriescounter.Alimento
import ipca.projeto.caloriescounter.R
import ipca.projeto.caloriescounter.databinding.FragmentHomeBinding

class HomeFragment : Fragment(){
    private val sharedAlimentoViewModel: SharedAlimentoViewModel by activityViewModels()
    var alimentos : List<Alimento> = arrayListOf(
        Alimento("Banana", 9.8, 13.2, 25.5, 19.1, 77.6, 90.1, 4.8, ""),
        Alimento("Batata", 11.3, 10.5, 28.7, 14.8, 90.2, 85.6, 6.3, ""),
        Alimento("Queijo", 14.5, 11.9, 30.3, 17.2, 85.7, 89.8, 5.9, ""),
        Alimento("Frango", 16.1, 12.8, 33.6, 15.9, 81.3, 88.7, 4.2, ""),
        Alimento("Salmão", 12.7, 11.1, 29.8, 16.7, 88.4, 86.9, 5.5, "")
    )
    val alimentosAdapter = AlimentosAdapter()

    var selectedAlimentos : List<Alimento> = arrayListOf()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchView : SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listViewAlimento.adapter = alimentosAdapter

        // Get reference to the SearchView
        searchView = binding.searchView

        // Set query text listener for the SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter the list when text changes
                alimentosAdapter.getFilter().filter(newText)
                return true
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class AlimentosAdapter : BaseAdapter() {
        var filteredAlimentos: List<Alimento> = listOf()
        init {
            // Initialize filteredAlimentos with the entire list initially
            filteredAlimentos = alimentos
        }

        // Implement the getFilter method from Filterable interface
        fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val queryString = constraint.toString().toLowerCase()

                    val filteredList = if (queryString.isEmpty()) {
                        // If the query is empty, return the entire list
                        alimentos
                    } else {
                        // Filter the list based on the query
                        alimentos.filter { alimento ->
                            alimento.nome!!.toLowerCase().contains(queryString)
                        }
                    }

                    val filterResults = FilterResults()
                    filterResults.values = filteredList
                    return filterResults
                }

                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    // Update the filtered list and notify adapter about the changes
                    filteredAlimentos = results?.values as List<Alimento>
                    notifyDataSetChanged()
                }
            }
        }

        override fun getCount(): Int {
            return filteredAlimentos.size
        }

        override fun getItem(position: Int): Any {
            return filteredAlimentos[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_alimento, parent, false)

            val imageView = rootView.findViewById<ImageView>(R.id.ImageView)
            val textViewAlimento = rootView.findViewById<TextView>(R.id.textViewAlimento)
            val textViewCals = rootView.findViewById<TextView>(R.id.textViewCals)
            val textViewNutrition = rootView.findViewById<TextView>(R.id.textViewNutrition)
            val button = rootView.findViewById<Button>(R.id.button)
            val fatText = rootView.findViewById<TextView>(R.id.fatText)
            val carbsText = rootView.findViewById<TextView>(R.id.carbsText)
            val sugarText = rootView.findViewById<TextView>(R.id.sugarText)
            val proteinText = rootView.findViewById<TextView>(R.id.proteinText)
            val sodiumText = rootView.findViewById<TextView>(R.id.sodiumText)
            val fiberText = rootView.findViewById<TextView>(R.id.fiberText)

            val currentAlimento = filteredAlimentos[position]

            val fat = currentAlimento.fat.toString()
            val carb = currentAlimento.carb.toString()
            val sugar = currentAlimento.sugar.toString()
            val protein = currentAlimento.protein.toString()
            val sodium = currentAlimento.sodium.toString()
            val fiber = currentAlimento.fiber.toString()
            fatText.text = "Fat: $fat"
            carbsText.text = "Carbs: $carb"
            sugarText.text = "Sugars: $sugar"
            proteinText.text = "Protein: $protein"
            sodiumText.text = "Sodium: $sodium"
            fiberText.text = "Fiber: $fiber"

            textViewAlimento.text = currentAlimento.nome
            var cal = currentAlimento.cal.toString()
            textViewCals.text = "Calorias: ${cal} / 100g"
            textViewNutrition.text = "Valores nutricionais / 100g"

            button.setOnClickListener {
                val alimentoExists = sharedAlimentoViewModel.selectedAlimentos.value?.any { it.nome == currentAlimento.nome }

                if (alimentoExists == false) {
                    sharedAlimentoViewModel.addSelectedAlimento(currentAlimento)
                    // Inform the ViewModel about the updated list
                    Toast.makeText(context, "${currentAlimento.nome} adicionado aos selecionados", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "${currentAlimento.nome} já foi adicionado", Toast.LENGTH_SHORT).show()
                }
            }

            val nutrition = arrayListOf<TextView>(
                fatText,carbsText,sugarText,proteinText,sodiumText,fiberText
            )

            textViewNutrition.setOnClickListener {
                val visibility = if (fatText.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                nutrition.forEach { textView ->
                    textView.visibility = visibility
                }
            }

            return rootView
        }
    }
}
