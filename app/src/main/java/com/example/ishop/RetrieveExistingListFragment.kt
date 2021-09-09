package com.example.ishop


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ishop.database.GroceryItemListDatabase
import com.example.ishop.databinding.FragmentRetrieveExistingListBinding
import com.google.android.material.snackbar.Snackbar

class RetrieveExistingListFragment : Fragment(), AdapterView.OnItemSelectedListener {
    var selectedList : String = ""
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        selectedList = parent.getItemAtPosition(pos) as String
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: FragmentRetrieveExistingListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_retrieve_existing_list, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = GroceryItemListDatabase.getInstance(application).groceryItemDatabaseDao

        val viewModelFactory = RetrieveExistingListViewModelFactory(dataSource, application)

        val retrieveViewModel = ViewModelProvider(this, viewModelFactory).get(RetrieveExistingListViewModel::class.java)

        retrieveViewModel.setNameString()

        val spinner = binding.existingListsSpinner
        spinner.onItemSelectedListener = this

        retrieveViewModel.nameList.observe(viewLifecycleOwner, Observer { nameList : Array<String> ->
            nameList.let {
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    application.applicationContext,
                    android.R.layout.simple_spinner_item, nameList
                )

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
                binding.retrieveExistingListViewModel = retrieveViewModel
                binding.lifecycleOwner = this

                binding.existingListsButton.setOnClickListener{
                    if(selectedList!=""){ retrieveViewModel.setCategories(selectedList)}
                    else{
                        Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.name_not_valid),
                        Snackbar.LENGTH_LONG
                    ).show() }
                }

                retrieveViewModel.itemSelected.observe(viewLifecycleOwner, Observer { itemSelected: Boolean ->
                    itemSelected.let {
                        this.findNavController().navigate(
                            RetrieveExistingListFragmentDirections.
                            actionRetrieveExistingListFragmentToShoppingFragment(selectedList,
                                retrieveViewModel.listCategories.value))
                    }
                })
            }
        })

        return binding.root
    }
}