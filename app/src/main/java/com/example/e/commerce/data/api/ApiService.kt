package com.example.e.commerce.data.api

import com.example.e.commerce.data.model.Product
import io.reactivex.Single

interface ApiService {

    fun getProducts(): Single<List<Product>>

}