package ipca.projeto.caloriescounter.ui.home

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ipca.projeto.caloriescounter.Alimento
import ipca.projeto.caloriescounter.R

class AlimentoFragment : Fragment() {

    var articles: List<Alimento> = arrayListOf<Alimento>()
    val articlesAdapter = ArticleListAdapter()

    private var _binding: FragmentAlimentoBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class ArticleListAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return articles.size
        }

        override fun getItem(position: Int): Any {
            return articles[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_alimento,parent,false)
            val textViewTitle = rootView.findViewById<TextView>(R.id.textViewTitle)
            val textViewDescription = rootView.findViewById<TextView>(R.id.textViewDescription)
            val textViewDate = rootView.findViewById<TextView>(R.id.textViewData)
            val imageView = rootView.findViewById<ImageView>(R.id.imageView)

            textViewTitle.text = articles[position].title
            textViewDescription.text = articles[position].description
            textViewDate.text = articles[position].publishedAt.toShortDateTime()

            articles[position].urlToImage?.let { url ->
                Backend.fetchImage(lifecycleScope, url){ bitmap ->
                    bitmap?.let {
                        imageView.setImageBitmap(it)
                    }
                }
            }
            rootView.setOnClickListener {
                //val intent = Intent(this@MainActivity, ArticleDetailActivity::class.java)
                //intent.putExtra(ArticleDetailActivity.URL, articles[position].url )
                //startActivity(intent)
            }


            return rootView
        }

    }
}