package com.example.ishop.manageCategories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.ishop.*
import com.example.ishop.database.GroceryItemListDatabase
import com.example.ishop.databinding.FragmentManageCurrentListsBinding

class ManageCategoriesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentManageCurrentListsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_manage_categories, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = GroceryItemListDatabase.getInstance(application).groceryItemDatabaseDao

//        val viewModelFactory = ShoppingViewModelFactory(dataSource, application)

//        val shoppingViewModel = ViewModelProvider(this, viewModelFactory).get(ShoppingViewModel::class.java)

//        binding.shoppingViewModel = shoppingViewModel
        binding.lifecycleOwner = this

//        val adapter = ItemAdapterShopping()
//        binding.groceryList.adapter = adapter
//
//        shoppingViewModel.groceryItems.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.submitList(it)
//            }
//        })

        return binding.root
    }
}