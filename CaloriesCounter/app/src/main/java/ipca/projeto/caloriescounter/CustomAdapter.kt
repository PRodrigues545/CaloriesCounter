package ipca.projeto.caloriescounter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val dataList: List<Alimento>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        val mainText: TextView = itemView.findViewById(R.id.mainText)
        val calsText: TextView = itemView.findViewById(R.id.calsText)
        val fatText: TextView = itemView.findViewById(R.id.fatText)
        val carbsText: TextView = itemView.findViewById(R.id.carbsText)
        val sugarText: TextView = itemView.findViewById(R.id.sugarText)
        val proteinText: TextView = itemView.findViewById(R.id.proteinText)
        val sodiumText: TextView = itemView.findViewById(R.id.sodiumText)
        val fiberText: TextView = itemView.findViewById(R.id.fiberText)

        val nutrition = arrayListOf<TextView>(
            fatText,carbsText,sugarText,proteinText,sodiumText,fiberText
        )

        init {
            itemView.setOnClickListener {
                val visibility = if (fatText.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                nutrition.forEach { textView ->
                    textView.visibility = visibility
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_alimento, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mainText.text = dataList[position].nome
        val cal = dataList[position].cal.toString()
        holder.calsText.text = "Calories: $cal"
        // Set additional text for the hidden text view if needed
        val fat = dataList[position].fat.toString()
        val carb = dataList[position].carb.toString()
        val sugar = dataList[position].sugar.toString()
        val protein = dataList[position].protein.toString()
        val sodium = dataList[position].sodium.toString()
        val fiber = dataList[position].fiber.toString()
        holder.fatText.text = "Fat: $fat"
        holder.carbsText.text = "Carbs: $carb"
        holder.sugarText.text = "Sugars: $sugar"
        holder.proteinText.text = "Protein: $protein"
        holder.sodiumText.text = "Sodium: $sodium"
        holder.fiberText.text = "Fiber: $fiber"

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}