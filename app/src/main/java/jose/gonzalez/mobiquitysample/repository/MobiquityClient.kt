package jose.gonzalez.mobiquitysample.repository

import jose.gonzalez.mobiquitysample.model.MobiquityModel
import retrofit2.Call
import retrofit2.http.GET

interface MobiquityClient {

    @GET(".")
    fun getListOfProductsByCategory(): Call<List<MobiquityModel>>

}