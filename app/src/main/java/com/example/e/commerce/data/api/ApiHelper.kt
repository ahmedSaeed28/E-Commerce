package com.example.e.commerce.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getProducts() = apiService.getProducts()

}