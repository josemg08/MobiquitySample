package jose.gonzalez.mobiquitysample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import jose.gonzalez.mobiquitysample.R
import jose.gonzalez.mobiquitysample.model.ProductModel
import jose.gonzalez.mobiquitysample.repository.MobiquityService
import jose.gonzalez.mobiquitysample.viewmodel.MobiquityMainViewModel

class ProductDetailFragment : Fragment() {

    private lateinit var name: TextView
    private lateinit var description: TextView
    private lateinit var price: TextView
    private lateinit var currency: TextView

    private lateinit var toolbarImage: ImageView

    private lateinit var viewModel: MobiquityMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initResources()
        initViewModel()
        subscribeToData()
    }

    private fun initViewModel() {
        viewModel = activity?.run {
            ViewModelProvider(this).get(MobiquityMainViewModel::class.java)
        }!!
    }

    private fun subscribeToData() {
        viewModel.productSelected.observe(viewLifecycleOwner, Observer<ProductModel> {
            setProductData(it)
        })
    }

    private fun initResources() {
        name = view!!.findViewById(R.id.title)
        description = view!!.findViewById(R.id.description)
        price = view!!.findViewById(R.id.price)
        currency = view!!.findViewById(R.id.currency)
        toolbarImage = view!!.findViewById(R.id.header)
    }

    private fun setProductData(product: ProductModel) {
        name.text = product.name
        description.text = product.description
        price.text = product.salePrice.amount
        currency.text = product.salePrice.currency
        setImageUrl(product)
    }

    private fun setImageUrl(product: ProductModel) {
        Glide
            .with(this)
            .load(MobiquityService.SERVICE_URL + product.url)
            .into(toolbarImage)
    }

}