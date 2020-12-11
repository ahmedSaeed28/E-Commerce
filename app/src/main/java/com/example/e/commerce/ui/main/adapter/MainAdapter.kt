package com.example.e.commerce.ui.main.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e.commerce.R
import com.example.e.commerce.data.model.Product
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.android.synthetic.main.view_more_item_layout.view.*
import java.util.*


class MainAdapter(
        private var products: ArrayList<Product>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    private lateinit var searchedText: String
    private var shoMore = false

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            if (product.title.isNotEmpty()) {
                val startPos: Int = product.title.toLowerCase(Locale.US).indexOf(searchedText.toLowerCase(Locale.US))
                val endPos: Int = startPos + searchedText.length
                if (startPos != -1) {
                    val spannable: Spannable = SpannableString(product.title)
                    val blueColor = ColorStateList(arrayOf(intArrayOf()), intArrayOf(Color.MAGENTA))
                    val highlightSpan = TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null)
                    spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    itemView.textViewTitle.text = spannable
                }
            }
            itemView.textViewDescription.text = product.description
            itemView.textViewPrice.text = product.price.toString() + " EG"
            itemView.textViewRate.rating = product.rating.toFloat();
            Glide.with(itemView.imageViewPicture.context)
                .load(product.picture)
                .into(itemView.imageViewPicture)
        }

        fun bind() {
            itemView.viewMore.setOnClickListener(View.OnClickListener {
                changeShowMore(true)
                notifyDataSetChanged()
            })
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position==ConstNumbers.BUTTON_POSITION-1 && !shoMore -> ViewTypes.BUTTON_ITEM
            else -> ViewTypes.NORMAL_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        when (viewType) {
            ViewTypes.NORMAL_ITEM -> DataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.item_layout, parent,
                            false
                    )
            )
            else -> DataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.view_more_item_layout, parent,
                            false
                    )
            )
        }

    override fun getItemCount(): Int =
        when{
            products.size>ConstNumbers.BUTTON_POSITION && !shoMore -> ConstNumbers.BUTTON_POSITION
            products.size>ConstNumbers.MAX ->  ConstNumbers.MAX
            else -> products.size
        }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        when {
            position == ConstNumbers.BUTTON_POSITION-1 && !shoMore -> holder.bind()
            else -> holder.bind(products[position])
        }

    fun updateData(list: List<Product>, s: String) {
        changeShowMore(false)
        searchedText = s
        products.clear()
        products.addAll(list)
    }

    fun changeShowMore(show: Boolean){
        shoMore = show
    }

    private object ViewTypes {
        const val BUTTON_ITEM = 1
        const val NORMAL_ITEM = 2
    }

    private object ConstNumbers {
        const val BUTTON_POSITION = 11
        const val MAX = 20
    }

}