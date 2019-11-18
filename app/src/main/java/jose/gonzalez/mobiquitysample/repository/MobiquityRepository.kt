package jose.gonzalez.mobiquitysample.repository

import androidx.lifecycle.MutableLiveData
import jose.gonzalez.mobiquitysample.model.MobiquityModel

class MobiquityRepository {

    private val mobiquityService = MobiquityService()

    fun getListOfProductsByCategory(): MutableLiveData<List<MobiquityModel>> {
        return mobiquityService.fetchListOfProductsByCategory()
    }

}