package jose.gonzalez.mobiquitysample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import jose.gonzalez.mobiquitysample.model.MobiquityModel
import jose.gonzalez.mobiquitysample.model.ProductModel
import jose.gonzalez.mobiquitysample.repository.MobiquityRepository

class MobiquityMainViewModel(application: Application) : AndroidViewModel(application) {

    val mutableLiveData : MutableLiveData<List<MobiquityModel>> = MobiquityRepository().getListOfProductsByCategory()

    var productSelected = MutableLiveData<ProductModel>()

    fun onProductSelected(headersId: String, productId: String) {
        for (mobiquityModel in (mutableLiveData.value!!)) {
            if (mobiquityModel.id == productId) {
                break
            }
            (mobiquityModel.products).forEach {
                if (it.id == productId && it.categoryId == headersId) {
                    productSelected.value = it
                    return
                }
            }
        }
    }

}