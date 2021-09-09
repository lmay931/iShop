package com.example.ishop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ishop.database.GroceryItemListDatabase
import com.example.ishop.databinding.FragmentManageListsBinding
import com.google.android.material.snackbar.Snackbar

class ManageListsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentManageListsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_manage_lists, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = GroceryItemListDatabase.getInstance(application).groceryItemDatabaseDao

        val viewModelFactory = ManageListsViewModelFactory(dataSource, application)

        val manageListsViewModel =
            ViewModelProvider(this, viewModelFactory).get(ManageListsViewModel::class.java)

        binding.manageListsViewModel = manageListsViewModel
        binding.lifecycleOwner = this

        manageListsViewModel.getLists()

        val adapter = ItemAdapterSimpleString()
        binding.existingLists.adapter = adapter

        binding.clearAllListsButton.setOnClickListener {
            manageListsViewModel.setSnackBar()
            manageListsViewModel.deleteALL()
            adapter.submitList(null)
    }

        manageListsViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.all_lists_deleted),
                    Snackbar.LENGTH_LONG
                ).show()
                manageListsViewModel.doneShowingSnackBar()
            }
        })

        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    manageListsViewModel.remove(adapter.getItemByPos(viewHolder.adapterPosition))
                }
            })

        itemTouchHelper.attachToRecyclerView(binding.existingLists)

        manageListsViewModel.nameList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })



        return binding.root
    }


}
