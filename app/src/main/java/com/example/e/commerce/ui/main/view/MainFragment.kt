package com.example.e.commerce.ui.main.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e.commerce.R
import com.example.e.commerce.data.model.Product
import com.example.e.commerce.ui.base.ViewModelFactory
import com.example.e.commerce.ui.main.adapter.MainAdapter
import com.example.e.commerce.ui.main.viewmodel.MainViewModel
import com.example.e.commerce.utils.Status
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory.getInstance()
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.computeVerticalScrollOffset()==0) {
                    scrollTop.visibility = View.GONE
                } else {
                    scrollTop.visibility = View.VISIBLE
                }
            }
        })
        scrollTop.setOnClickListener(View.OnClickListener {
            recyclerView.smoothScrollToPosition(0)
        })
    }

    private fun setupObserver() {
        mainViewModel.getSearchedProducts().observe(this, Observer {
            when {
                it.isEmpty() -> {
//                    scrollTop.visibility = View.GONE
                    noProduct.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                else -> {
                    noProduct.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
//                    scrollTop.visibility = View.VISIBLE
                    renderList(it)
                }
            }
        })
    }

    private fun renderList(products: List<Product>) {
        adapter.updateData(products, mainViewModel.getSearchedTitle().value!!)
        adapter.notifyDataSetChanged()
    }
}