package com.example.e.commerce.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e.commerce.data.api.ApiHelper
import com.example.e.commerce.data.repository.MainRepository
import com.example.e.commerce.ui.main.viewmodel.MainViewModel


class ViewModelFactory() : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel.getInstance() as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

    companion object {
        private var instance : ViewModelFactory? = null
        fun getInstance() =
            instance ?: synchronized(ViewModelFactory::class.java){
                instance ?: ViewModelFactory().also { instance = it }
            }
    }

}