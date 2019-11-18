package jose.gonzalez.mobiquitysample.view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jose.gonzalez.mobiquitysample.R
import jose.gonzalez.mobiquitysample.repository.MobiquityService.Companion.SERVICE_URL

class ProductsItemAdapter(private val list: List<ProductCategoryModel>,
                          private val clickListener: View.OnClickListener)
    : RecyclerView.Adapter<ProductsItemAdapter.ViewHolder>(){

    private lateinit var context: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.product_item_adapter, viewGroup, false)
        view.setOnClickListener(clickListener)
        context = viewGroup.context
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (list[position].isHeader) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                viewHolder.cardView.setBackgroundColor(viewHolder.itemView.context.getColor(R.color.colorAccent))
            }
            viewHolder.productContainer.visibility = View.INVISIBLE
            viewHolder.header.visibility = View.VISIBLE
            viewHolder.header.text = list[position].name
        } else {
            viewHolder.title.text = list[position].name
            setImageUrl(position, viewHolder)
        }
    }

    private fun setImageUrl(position: Int, viewHolder: ViewHolder) {
        val requestOptions: RequestOptions by lazy {
            RequestOptions()
                .placeholder(R.drawable.ic_question)
        }

        Glide
            .with(context)
            .load(SERVICE_URL + list[position].url)
            .apply(requestOptions)
            .into(viewHolder.photo)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val productContainer: ConstraintLayout = itemView.findViewById(R.id.productContainer)
        val header = itemView.findViewById<TextView>(R.id.header)!!
        val title = itemView.findViewById<TextView>(R.id.title)!!
        val photo = itemView.findViewById<ImageView>(R.id.photo)!!
    }

    class ProductCategoryModel(val id: String, val isHeader: Boolean, val name: String,
                               val url: String, val headerId: String)

}