package shopping.list.ishop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import shopping.list.ishop.databinding.FragmentAddItemsBinding
import shopping.list.ishop.database.GroceryItemListDatabase


class AddItemsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentAddItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_items, container, false)

        val arguments = shopping.list.ishop.AddItemsFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dataSource = GroceryItemListDatabase.getInstance(application).groceryItemDatabaseDao

        val viewModelFactory = shopping.list.ishop.AddItemsViewModelFactory(
            dataSource,
            arguments.listName,
            application
        )
        binding.shoppingHeader.text = arguments.listName

        val addItemsViewModel = ViewModelProvider(this, viewModelFactory).get(shopping.list.ishop.AddItemsViewModel::class.java)
        binding.addItemsViewModel = addItemsViewModel
        binding.lifecycleOwner = this

        Toast.makeText(
            this.requireContext(),
            "Hint: Swipe right on items to remove them",
            Toast.LENGTH_SHORT
        ).show()

        addItemsViewModel.navigateToShopping.observe(viewLifecycleOwner, Observer { nameList : String ->
            nameList.let {
                this.findNavController().navigate(
                    shopping.list.ishop.AddItemsFragmentDirections.actionAddItemsFragmentToShoppingFragment(
                        nameList,
                        arguments.groceryCategories
                    )
                )
            }
        })

        binding.submitButton.setOnClickListener {
            addItemsViewModel.setNavigateToShopping()
        }

        val fruitAdapter = shopping.list.ishop.ItemAdapterAddItems(
            addItemsViewModel,
            viewLifecycleOwner,
            dataSource
        )
        binding.newItemRecyclerview.adapter = fruitAdapter
        fruitAdapter.submitList(arguments.groceryCategories?.toList())

        return binding.root
    }
}


