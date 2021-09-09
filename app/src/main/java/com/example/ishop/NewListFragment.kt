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
import com.example.ishop.databinding.FragmentNewListBinding

class NewListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentNewListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_new_list, container, false)

        val arguments = NewListFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dataSource = GroceryItemListDatabase.getInstance(application).groceryItemDatabaseDao

        if (arguments.showItems) {
            binding.addedFruitVegTextView.visibility = View.VISIBLE
            binding.addedDairyTextView.visibility = View.VISIBLE
            binding.addedMeatTextView.visibility = View.VISIBLE
            binding.addedSeafoodTextView.visibility = View.VISIBLE
            binding.addedAlcoholTextView.visibility = View.VISIBLE
        }

        val viewModelFactory = NewListViewModelFactory(dataSource, arguments.listName, application)
        binding.shoppingHeader.text = arguments.listName

        val groceryListViewModel = ViewModelProvider(this, viewModelFactory).get(NewListViewModel::class.java)
        binding.newListViewModel = groceryListViewModel
        binding.lifecycleOwner = this

        fun addFruitItem() {
            val text = binding.addFruitVegeItem.text.toString()
            if (text == "") {
                return
            }
            binding.apply {
                addedFruitVegTextView.visibility = View.VISIBLE
                binding.addFruitVegeItem.text.clear()
            }
            groceryListViewModel.addItem( "fruit",text)
        }

        fun addDairyItem() {
            val text = binding.addDairyItem.text.toString()
            if (text == "") {
                return
            }
            binding.apply {
                addedDairyTextView.visibility = View.VISIBLE
                binding.addDairyItem.text.clear()
            }
            groceryListViewModel.addItem( "dairy",text)
        }

        fun addMeatItem() {
            val text = binding.addMeatItem.text.toString()
            if (text == "") {
                return
            }
            binding.apply {
                addedMeatTextView.visibility = View.VISIBLE
                binding.addMeatItem.text.clear()
            }
            groceryListViewModel.addItem( "meat",text)
        }

        fun addSeafoodItem() {
            val text = binding.addFishSeafoodItem.text.toString()
            if (text == "") {
                return
            }
            binding.apply {
                addedSeafoodTextView.visibility = View.VISIBLE
                binding.addFishSeafoodItem.text.clear()
            }
            groceryListViewModel.addItem( "seafood",text)
        }

        fun addAlcoholItem() {
            val text = binding.addAlcoholItem.text.toString()
            if (text == "") {
                return
            }
            binding.apply {
                addedAlcoholTextView.visibility = View.VISIBLE
                binding.addAlcoholItem.text.clear()
            }
            groceryListViewModel.addItem( "alcohol",text)
        }

        binding.buttonFruitVeg.setOnClickListener {
            addFruitItem()
        }
        binding.buttonDairy.setOnClickListener {
            addDairyItem()
        }
        binding.buttonMeat.setOnClickListener {
            addMeatItem()
        }
        binding.buttonSeafood.setOnClickListener {
            addSeafoodItem()
        }
        binding.buttonAlcohol.setOnClickListener {
            addAlcoholItem()
        }

        groceryListViewModel.navigateToShopping.observe(viewLifecycleOwner, Observer { nameList : String ->
            nameList.let {
                this.findNavController().navigate(
                    NewListFragmentDirections
                        .actionNewListFragmentToShoppingFragment(nameList,arguments.groceryCategories))
            }
        })

        binding.submitButton.setOnClickListener {
            groceryListViewModel.setNavigateToShopping()
        }

        val fruitAdapter = ItemAdapterNewList()
        binding.addedFruitVegTextView.adapter = fruitAdapter
        groceryListViewModel.fruitVegList.observe(viewLifecycleOwner, Observer {
            it?.let {
                fruitAdapter.submitList(it)
            }
        })

        val dairyAdapter = ItemAdapterNewList()
        binding.addedDairyTextView.adapter = dairyAdapter
        groceryListViewModel.dairyString.observe(viewLifecycleOwner, Observer {
            it?.let {
                dairyAdapter.submitList(it)
            }
        })

        val meatAdapter = ItemAdapterNewList()
        binding.addedMeatTextView.adapter = meatAdapter
        groceryListViewModel.meatString.observe(viewLifecycleOwner, Observer {
            it?.let {
                meatAdapter.submitList(it)
            }
        })

        val seafoodAdapter = ItemAdapterNewList()
        binding.addedSeafoodTextView.adapter = seafoodAdapter
        groceryListViewModel.seafoodString.observe(viewLifecycleOwner, Observer {
            it?.let {
                seafoodAdapter.submitList(it)
            }
        })

        val alcAdapter = ItemAdapterNewList()
        binding.addedAlcoholTextView.adapter = alcAdapter
        groceryListViewModel.alcoholString.observe(viewLifecycleOwner, Observer {
            it?.let {
                alcAdapter.submitList(it)
            }
        })

        return binding.root
    }
}


