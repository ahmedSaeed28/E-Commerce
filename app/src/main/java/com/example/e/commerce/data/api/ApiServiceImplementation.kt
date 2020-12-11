package com.example.e.commerce.data.api

import android.util.Log
import com.example.e.commerce.data.model.Product
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class ApiServiceImplementation : ApiService {

    override fun getProducts(): Single<List<Product>> {
        Log.i("impl", "impl")
        return Rx2AndroidNetworking.get("https://5fd15a4cb485ea0016eee3ee.mockapi.io/products")
            .build()
            .getObjectListSingle(Product::class.java)
    }
}