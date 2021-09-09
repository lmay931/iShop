package com.example.ishop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ishop.database.GroceryItemListDatabase
import com.example.ishop.databinding.FragmentAddItemsBinding

class AddItemsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentAddItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_items, container, false)

        val arguments = AddItemsFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dataSource = GroceryItemListDatabase.getInstance(application).groceryItemDatabaseDao

        val viewModelFactory = AddItemsViewModelFactory(dataSource, arguments.listName, application)
        binding.shoppingHeader.text = arguments.listName

        val addItemsViewModel = ViewModelProvider(this, viewModelFactory).get(AddItemsViewModel::class.java)
        binding.addItemsViewModel = addItemsViewModel
        binding.lifecycleOwner = this

        addItemsViewModel.navigateToShopping.observe(viewLifecycleOwner, Observer { nameList : String ->
            nameList.let {
                this.findNavController().navigate(
                    AddItemsFragmentDirections.actionAddItemsFragmentToShoppingFragment(nameList,arguments.groceryCategories))
            }
        })

        binding.submitButton.setOnClickListener {
            addItemsViewModel.setNavigateToShopping()
        }

        val fruitAdapter = ItemAdapterAddItems(addItemsViewModel, viewLifecycleOwner,dataSource)
        binding.newItemRecyclerview.adapter = fruitAdapter
        fruitAdapter.submitList(arguments.groceryCategories?.toList())

        return binding.root
    }
}


