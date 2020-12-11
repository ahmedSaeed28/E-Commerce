package com.example.e.commerce.data.repository

import com.example.e.commerce.data.api.ApiHelper
import com.example.e.commerce.data.model.Product
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getProducts(): Single<List<Product>> {
        return apiHelper.getProducts()
    }

}