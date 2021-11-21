package com.example.nvesttask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nvesttask.adapter.ProductAdapter
import com.example.nvesttask.databinding.FragmentProductListBinding
import com.example.nvesttask.model.Product
import com.example.nvesttask.viewmodel.ProductViewModel

class ProductListFragment : Fragment() {

    private lateinit var viewModel: ProductViewModel
    lateinit var fragmentUserListBinding : FragmentProductListBinding
    var productAdapter : ProductAdapter? = null
    lateinit var sharedPreferences : SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentUserListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_product_list, container, false)

        return fragmentUserListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPreferences = requireContext().getSharedPreferences("ProductSharedPreference", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.getProductList()


        viewModel.products.observe(viewLifecycleOwner) {
            setAdapter(it)
        }
    }



    private fun setAdapter(list: List<Product>) {
        if (productAdapter == null) {
            productAdapter = ProductAdapter(requireContext(), list)
            fragmentUserListBinding.rvUserList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            fragmentUserListBinding.rvUserList.adapter = productAdapter
        } else {
            productAdapter?.updateList(list)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_search_options, menu)
        val item = menu.findItem(R.id.filter_menu)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productAdapter?.filter?.filter(newText)
                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                editor.putBoolean("isLoggedIn", false)
                editor.apply()
                findNavController().navigate(R.id.action_productListFragment_to_loginFragment)
            }
        }
        return true
    }


}