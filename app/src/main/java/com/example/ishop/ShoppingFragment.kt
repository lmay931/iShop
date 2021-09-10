package com.example.ishop

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ishop.database.GroceryItemListDatabase
import com.example.ishop.databinding.FragmentShoppingBinding
import com.google.android.material.snackbar.Snackbar

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

        val adapter = ItemAdapterShopping(shoppingViewModel,viewLifecycleOwner,dataSource)
        binding.groceryList.adapter = adapter

        setHasOptionsMenu(true)

        adapter.submitList(arguments.groceryCategories?.toMutableList())

        shoppingViewModel.readyToNavigate.observe(viewLifecycleOwner, Observer { readyToNavigate : Boolean ->
            readyToNavigate.let {
                this.findNavController().navigate(
                    ShoppingFragmentDirections.actionShoppingFragmentToDone())
            }
            shoppingViewModel.resetSwitches()
        })
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title == "Edit categories") {
            this.findNavController().navigate(
                ShoppingFragmentDirections
                    .actionShoppingFragmentToManageCategoriesFragment(ShoppingFragmentArgs.fromBundle(requireArguments()).listName, true))
        }
        if(item.title == "Edit grocery items")
            this.findNavController().navigate(
                ShoppingFragmentDirections
                    .actionShoppingFragmentToAddItemsFragment(ShoppingFragmentArgs.fromBundle(requireArguments()).listName, true,
                        ShoppingFragmentArgs.fromBundle(requireArguments()).groceryCategories))
        return true
    }
}