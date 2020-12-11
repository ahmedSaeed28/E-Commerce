package com.example.e.commerce.ui.main.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e.commerce.data.model.Product
import com.example.e.commerce.data.repository.MainRepository
import com.example.e.commerce.utils.Resource
import io.reactivex.schedulers.Schedulers

class MainViewModel() : ViewModel() {

    private val products = MutableLiveData<Resource<List<Product>>>()
    private val searchedProducts = MutableLiveData<List<Product>>()
    private val searchedTitle = MutableLiveData<String>()

    companion object {
        private var instance : MainViewModel? = null
        fun getInstance() =
            instance ?: synchronized(MainViewModel::class.java){
                instance ?: MainViewModel().also { instance = it }
            }
    }

    @SuppressLint("CheckResult")
    fun fetchProducts(mainRepository: MainRepository) {
        products.postValue(Resource.loading(null))
        mainRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .subscribe({ productList ->
                products.postValue(Resource.success(productList))
            }, { _ ->
                products.postValue(Resource.error("Something Went Wrong", null))
            })
    }

    fun getProducts(): LiveData<Resource<List<Product>>> {
        return products
    }

    fun getSearchedProducts(): LiveData<List<Product>> {
        return searchedProducts
    }

    fun getSearchedTitle() : LiveData<String>{
        return searchedTitle
    }

    fun search(s: String){
        searchedTitle.value = s
        val list = products.value!!.data
        var searchedList = arrayListOf<Product>()
        for (i in list!!.indices) {
            if (list[i].title.toLowerCase().contains(s.toLowerCase()))
                searchedList.add(list[i])
        }
        if(searchedList.size == list.size) {
            searchedList.clear()
        }
        searchedProducts.value = searchedList
    }
}