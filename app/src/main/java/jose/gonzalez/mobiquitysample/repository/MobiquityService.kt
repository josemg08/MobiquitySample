package jose.gonzalez.mobiquitysample.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import jose.gonzalez.mobiquitysample.model.MobiquityModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MobiquityService {

    fun fetchListOfProductsByCategory(): MutableLiveData<List<MobiquityModel>> {
        val mutableLiveData = MutableLiveData<List<MobiquityModel>>()

        val retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MobiquityClient::class.java)
        val jsonCall = service.getListOfProductsByCategory()
        jsonCall.enqueue(object : Callback<List<MobiquityModel>> {
            override fun onResponse(call: Call<List<MobiquityModel>>, response: Response<List<MobiquityModel>>) {
                mutableLiveData.value = ((response.body() as ArrayList<MobiquityModel>))
            }

            override fun onFailure(call: Call<List<MobiquityModel>>, t: Throwable) {
                Log.e("", "")
                //TODO
            }
        })

        return mutableLiveData
    }

    companion object {
        const val SERVICE_URL = "http://mobcategories.s3-website-eu-west-1.amazonaws.com"
    }

}