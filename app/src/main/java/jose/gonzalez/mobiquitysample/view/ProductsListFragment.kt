package jose.gonzalez.mobiquitysample.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jose.gonzalez.mobiquitysample.R
import jose.gonzalez.mobiquitysample.model.MobiquityModel
import jose.gonzalez.mobiquitysample.viewmodel.MobiquityMainViewModel

class ProductsListFragment : Fragment(), View.OnClickListener {

    private lateinit var cantAccessInfoText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private lateinit var viewModel: MobiquityMainViewModel

    private lateinit var listWithHeaders: List<ProductsItemAdapter.ProductCategoryModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initResources()
        showProgress()
        initViewModel()
        subscribeToData()
    }

    private fun initViewModel() {
        viewModel = activity?.run {
            ViewModelProvider(this).get(MobiquityMainViewModel::class.java)
        }!!
    }

    private fun subscribeToData() {
        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer<List<MobiquityModel>> {
            if (it.isEmpty()) {
                showErrorMessage()
            } else {
                initRecyclerView(it)
            }
        })
    }

    private fun initRecyclerView(list: List<MobiquityModel>) {
        listWithHeaders = getListWithHeaders(list)
        showContent()
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = ProductsItemAdapter(listWithHeaders, this)
    }

    private fun getListWithHeaders(list: List<MobiquityModel>) : List<ProductsItemAdapter.ProductCategoryModel> {
        val productCategoryModelList = arrayListOf<ProductsItemAdapter.ProductCategoryModel>()
        list.forEach { mobiquityModel ->
            productCategoryModelList.add(ProductsItemAdapter.ProductCategoryModel(mobiquityModel.id, true, mobiquityModel.name,
                "", mobiquityModel.id))
            mobiquityModel.products.forEach { product ->
                productCategoryModelList.add(ProductsItemAdapter.ProductCategoryModel(product.id, false, product.name,
                    product.url, product.categoryId))
            }
        }
        return productCategoryModelList
    }

    private fun initResources() {
        cantAccessInfoText = view!!.findViewById(R.id.cantAccessInfo)
        progressBar = view!!.findViewById(R.id.progressBar)
        recyclerView = view!!.findViewById(R.id.BanksRecyclerView)
    }

    override fun onClick(view: View?) {
        val itemPosition = view?.let { recyclerView.getChildLayoutPosition(it) }
        viewModel.onProductSelected(listWithHeaders[itemPosition!!].headerId, listWithHeaders[itemPosition].id)

        try {
            view.findNavController().navigate(ProductsListFragmentDirections.actionProductListToProductDetail())
            //findNavController().navigate(ProductsListFragmentDirections.actionProductListToProductDetail())
        } catch (e: IllegalStateException) {
            Log.e(this.tag, e.message)
        }
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
        cantAccessInfoText.visibility = View.INVISIBLE
    }

    private fun showContent() {
        progressBar.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
        cantAccessInfoText.visibility = View.INVISIBLE
    }

    private fun showErrorMessage() {
        progressBar.visibility = View.INVISIBLE
        recyclerView.visibility = View.INVISIBLE
        cantAccessInfoText.visibility = View.VISIBLE
    }

}