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
import com.google.android.material.snackbar.Snackbar

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

        val adapter = ItemAdapterStrings()
        binding.existingCategories.adapter = adapter

        manageCategoriesViewModel.getLists()

        binding.addCategoryButton.setOnClickListener {
            if(binding.addCategory.text.toString()==""){ manageCategoriesViewModel.setSnackBar()}
            else{
                manageCategoriesViewModel.addCategory(binding.addCategory.text.toString())
                adapter.submitList(manageCategoriesViewModel.categories)
                binding.addCategory.text.clear()
            }
        }

        manageCategoriesViewModel.navigateToNewList.observe(viewLifecycleOwner, Observer { nameList : String ->
            nameList.let {
                this.findNavController().navigate(
                    ManageCategoriesFragmentDirections.actionManageCategoriesFragmentToNewListFragment(nameList,arguments.showItems,
                        manageCategoriesViewModel.categories?.toTypedArray()
                    ))
            }
        })

        manageCategoriesViewModel.liveCategories.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (manageCategoriesViewModel.categories == listOf("")){
                    manageCategoriesViewModel.setCategories()
                }
                if (manageCategoriesViewModel.categories != listOf("")){
                    adapter.submitList(manageCategoriesViewModel.categories)
                }
            }
        })

        manageCategoriesViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.name_not_valid),
                    Snackbar.LENGTH_LONG
                ).show()
                manageCategoriesViewModel.doneShowingSnackBar()
            }
        })

        return binding.root
    }
}