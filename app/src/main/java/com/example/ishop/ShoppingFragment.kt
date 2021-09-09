package com.example.ishop

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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

        val adapter = ItemAdapterShopping()
        binding.groceryList.adapter = adapter

        setHasOptionsMenu(true)

        shoppingViewModel.groceryItems.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu,menu)
    }

    private fun navigateToNewList() {
        this.findNavController().navigate(
            ShoppingFragmentDirections
                .actionShoppingFragmentToNewListFragment(ShoppingFragmentArgs.fromBundle(requireArguments()).listName,
                    true,ShoppingFragmentArgs.fromBundle(requireArguments()).groceryCategories))
    }

    private fun navigateToManageCategories() {
        this.findNavController().navigate(
            ShoppingFragmentDirections
                .actionShoppingFragmentToManageCategoriesFragment(ShoppingFragmentArgs.fromBundle(requireArguments()).listName, true))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title == R.string.add_more_items.toString()) {
            navigateToNewList()
            return true
        }
        return if(item.title == R.string.edit_categories.toString()) {
            navigateToManageCategories()
            true
        } else{
            false
        }
        }
    }