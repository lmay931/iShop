package com.example.ishop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ishop.database.GroceryItemListDatabase
import com.example.ishop.databinding.FragmentManageCategoriesBinding

class ManageCategoriesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentManageCategoriesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_manage_categories, container, false
        )

        val arguments = ManageCategoriesFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dataSource = GroceryItemListDatabase.getInstance(application).groceryItemDatabaseDao

        val viewModelFactory = ManageCategoriesViewModelFactory(dataSource, arguments.listName, application)

        val manageCategoriesViewModel = ViewModelProvider(this, viewModelFactory).get(ManageCategoriesViewModel::class.java)

        binding.manageCategoriesViewModel = manageCategoriesViewModel
        binding.lifecycleOwner = this

        binding.proceedButton.setOnClickListener {
            manageCategoriesViewModel.setNavigateToNewList()
        }
        manageCategoriesViewModel.navigateToNewList.observe(viewLifecycleOwner, Observer { nameList : String ->
            nameList.let {
                this.findNavController().navigate(
                    ManageCategoriesFragmentDirections.actionManageCategoriesFragmentToNewListFragment(nameList,arguments.showItems))
            }
        })

        val adapter = ItemAdapterStrings()
        binding.existingCategories.adapter = adapter

        manageCategoriesViewModel.getLists()

        manageCategoriesViewModel.categories.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}