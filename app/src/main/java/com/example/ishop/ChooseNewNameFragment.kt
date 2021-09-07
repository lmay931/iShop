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
import com.example.ishop.databinding.FragmentChooseNameBinding
import com.google.android.material.snackbar.Snackbar

class ChooseNewNameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentChooseNameBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_choose_name, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = GroceryItemListDatabase.getInstance(application).groceryItemDatabaseDao

        val viewModelFactory = ChooseNewNameViewModelFactory(dataSource, application)

        val chooseNewNameViewModel =
            ViewModelProvider(this, viewModelFactory).get(ChooseNewNameViewModel::class.java)
        binding.chooseNewNameViewModel = chooseNewNameViewModel
        binding.lifecycleOwner = this

        binding.buttonSetListName.setOnClickListener {
            val newName = binding.setListName.text.toString()
            chooseNewNameViewModel.setNameList(newName)
        }

        chooseNewNameViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == 1) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.name_already_taken),
                    Snackbar.LENGTH_LONG
                ).show()
                chooseNewNameViewModel.doneShowingSnackBar()
            }
            if (it == 2) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.name_not_valid),
                    Snackbar.LENGTH_LONG
                ).show()
                chooseNewNameViewModel.doneShowingSnackBar()
            }
        })

        chooseNewNameViewModel.nameList.observe(viewLifecycleOwner, Observer { nameList : String ->
            nameList.let {
                this.findNavController().navigate(
                    ChooseNewNameFragmentDirections.
                    actionChooseNewNameFragmentToNewListFragment(nameList,false))
            }
        })

        return binding.root
    }

}