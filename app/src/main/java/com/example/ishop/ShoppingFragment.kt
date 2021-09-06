package com.example.ishop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.ishop.database.GroceryItemListDatabase
import com.example.ishop.databinding.FragmentShoppingBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ShoppingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ShoppingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentShoppingBinding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_shopping, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = GroceryItemListDatabase.getInstance(application).groceryItemDatabaseDao

        val arguments = ShoppingFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = ShoppingViewModelFactory(dataSource, arguments.listName, application)

        val shoppingViewModel = ViewModelProvider(this, viewModelFactory).get(ShoppingViewModel::class.java)

        binding.shoppingViewModel = shoppingViewModel
        binding.lifecycleOwner = this

        return binding.root
    }


}