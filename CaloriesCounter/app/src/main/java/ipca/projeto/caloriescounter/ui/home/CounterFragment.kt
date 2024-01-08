package ipca.projeto.caloriescounter.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
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

        // Inflate the options menu specific to this fragment
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.counter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Handle menu item clicks if needed
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_calculate -> {
                // Retrieve total calories and total grams from the adapter
                val totalCalories = selectedAlimentosAdapter.getTotalCalories()
                val totalGrams = selectedAlimentosAdapter.getTotalGrams()

                // Create a Toast message to display the totals
                val toastMessage = "Total Calories: $totalCalories\nTotal Grams: $totalGrams"
                Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
                return true
            }
            // Handle other menu item clicks as needed
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class SelectedAlimentoAdapter(private var selectedAlimentos: List<Alimento>) : BaseAdapter() {
        private var totalGrams: Double = 0.0
        private var totalCalories: Double = 0.0

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
            val grams = rootView.findViewById<EditText>(R.id.editTextGrams)

            // Retrieve the quantity input by the user (assuming it's entered via EditText)
            val inputQuantity = grams.text.toString().toIntOrNull() ?: currentAlimento.quantity // Use current quantity if input is invalid
            currentAlimento.quantity = inputQuantity // Update Alimento's quantity
            // Calculate total grams based on the updated quantity
            totalGrams += inputQuantity

            val fat = String.format("%.2f", (currentAlimento.fat * inputQuantity) / 100)
            val carb = String.format("%.2f", (currentAlimento.carb * inputQuantity) / 100)
            val sugar = String.format("%.2f", (currentAlimento.sugar * inputQuantity) / 100)
            val protein = String.format("%.2f", (currentAlimento.protein * inputQuantity) / 100)
            val sodium = String.format("%.2f", (currentAlimento.sodium * inputQuantity) / 100)
            val fiber = String.format("%.2f", (currentAlimento.fiber * inputQuantity) / 100)

            fatTextS.text = "Fat: $fat"
            carbsTextS.text = "Carbs: $carb"
            sugarTextS.text = "Sugars: $sugar"
            proteinTextS.text = "Protein: $protein"
            sodiumTextS.text = "Sodium: $sodium"
            fiberTextS.text = "Fiber: $fiber"

            // Calculate calories based on the entered quantity
            val cals = (currentAlimento.cal * inputQuantity) / 100
            totalCalories += cals
            textViewCalsS.text = "Calorias: $cals g"

            textViewAlimentoS.text = currentAlimento.nome
            textViewNutritionS.text = "Valores nutricionais"


            buttonRemove.setOnClickListener {
                // Here, you can remove the first Alimento from the list for demonstration purposes
                sharedAlimentoViewModel.selectedAlimentos.value?.let { selectedList ->
                    if (selectedList.isNotEmpty()) {
                        sharedAlimentoViewModel.removeSelectedAlimento(currentAlimento)
                        totalGrams -= inputQuantity // Adjust total grams when an Alimento is removed
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

        fun getTotalCalories(): String {
            return String.format("%.2f", totalCalories) // Format total calories with 2 decimal places
        }

        fun getTotalGrams(): String {
            return String.format("%.2f", totalGrams) // Format total grams with 2 decimal places
        }

        fun updateSelectedAlimentos(alimentos: List<Alimento>) {
            selectedAlimentos = alimentos
            notifyDataSetChanged()
        }
    }
}