package com.example.ishop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ishop.database.GroceryItemListDatabase
import com.example.ishop.databinding.FragmentManageListsBinding

class ManageListsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentManageListsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_manage_lists, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = GroceryItemListDatabase.getInstance(application).groceryItemDatabaseDao

        val viewModelFactory = ManageListsViewModelFactory(dataSource, application)

        val manageListsViewModel = ViewModelProvider(this, viewModelFactory).get(ManageListsViewModel::class.java)

        binding.manageListsViewModel = manageListsViewModel
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