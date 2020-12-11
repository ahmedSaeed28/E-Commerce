package com.example.e.commerce.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.e.commerce.R
import com.example.e.commerce.data.api.ApiHelper
import com.example.e.commerce.data.api.ApiServiceImplementation
import com.example.e.commerce.data.repository.MainRepository
import com.example.e.commerce.ui.base.ViewModelFactory
import com.example.e.commerce.ui.main.viewmodel.MainViewModel
import com.example.e.commerce.utils.Status
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupViewModel()
        setupObserver()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory.getInstance()
        ).get(MainViewModel::class.java)
        mainViewModel.fetchProducts(MainRepository(ApiHelper(ApiServiceImplementation())))
    }

    private fun setupObserver() {
        mainViewModel.getProducts().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    startActivity(
                        Intent(
                            this,
                            MainActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    )
                    finish()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}