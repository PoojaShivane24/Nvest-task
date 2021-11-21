package com.example.nvesttask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nvesttask.R
import com.example.nvesttask.databinding.ProductItemBinding
import com.example.nvesttask.model.Product
import java.util.*
import kotlin.collections.ArrayList

class ProductAdapter(context: Context, productList : List<Product>) : RecyclerView.Adapter<ProductAdapter.UserViewHolder>(), Filterable {

    private lateinit var productLists : MutableList<Product>
    private lateinit var productListAll : MutableList<Product>
    private var context : Context
    init {
        this.productLists = ArrayList(productList)
        this.context = context
        productListAll = ArrayList(this.productLists)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater  = LayoutInflater.from(parent.context)
        val userItemBinding : ProductItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.product_item, parent, false)
        return UserViewHolder(userItemBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val product = productLists[position]
        val title = "Title: "+product.title
        val price = "Price: "+product.price
        val description = "Description: " + product.description
        val category = "Category: "+product.category
        val rate = "Rate: " + product.rating.rate
        val count = "Count: " + product.rating.count

        holder.view.tvTitle.text = title
        holder.view.tvPrice.text = price
//        holder.view.tvDescription.text = description
        holder.view.tvCategory.text = category
        holder.view.tvRate.text = rate
        holder.view.tvCount.text = count
        Glide.with(context).load(product.image).into(holder.view.ivProduct)

    }

    override fun getItemCount(): Int {
        return productLists.size
    }

    fun updateList(list: List<Product>) {
        productLists = list as MutableList<Product>
        notifyItemInserted(list.size-1)
    }

    fun removeData(position: Int) {
        productLists.removeAt(position)
        notifyItemRemoved(position)
    }

    class UserViewHolder(val view : ProductItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun getFilter(): Filter {
        return filters
    }
    val filters = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            var filteredList : MutableList<Product> = ArrayList()
            if (p0.toString().isEmpty()) {
                filteredList.addAll(productListAll)
            } else {
                for (product in productListAll) {
                    if (product.title.lowercase(Locale.getDefault()).contains(p0.toString()
                            .lowercase(Locale.getDefault())) || product.price.toString().contains(p0.toString())) {
                        filteredList.add(product)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            productLists.clear()
            productLists.addAll(p1?.values as Collection<Product>)
            notifyDataSetChanged()
        }

    }


}