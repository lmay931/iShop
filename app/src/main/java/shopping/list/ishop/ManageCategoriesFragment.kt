package shopping.list.ishop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import shopping.list.ishop.database.GroceryItemListDatabase
import com.google.android.material.snackbar.Snackbar
import shopping.list.ishop.databinding.FragmentManageCategoriesBinding

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

        val adapter = ItemAdapterSimpleString()
        binding.existingCategories.adapter = adapter

        Toast.makeText(
            this.requireContext(),
            "Hint: Swipe right on categories to remove them",
            Toast.LENGTH_SHORT
        ).show()

        manageCategoriesViewModel.getLists()

        binding.addCategoryButton.setOnClickListener {
            if(binding.addCategory.text.toString()==""){ manageCategoriesViewModel.setSnackBar(1)}
            if(manageCategoriesViewModel.liveCategories.value?.contains(binding.addCategory.text.toString()) == true){
                manageCategoriesViewModel.setSnackBar(2)
            }
            else{
                manageCategoriesViewModel.addCategory(binding.addCategory.text.toString())
                adapter.submitList(manageCategoriesViewModel.categories)
                binding.addCategory.text.clear()
            }
        }

        manageCategoriesViewModel.navigateToNewList.observe(viewLifecycleOwner, Observer { nameList : String ->
            nameList.let {
                this.findNavController().navigate(
                    ManageCategoriesFragmentDirections.actionManageCategoriesFragmentToAddItemsFragment(nameList,arguments.showItems,
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
            if (it == 1) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.name_not_valid),
                    Snackbar.LENGTH_LONG
                ).show()
                manageCategoriesViewModel.doneShowingSnackBar()
            }
            if (it == 2) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.category_already_in_use),
                    Snackbar.LENGTH_LONG
                ).show()
                manageCategoriesViewModel.doneShowingSnackBar()
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
                    manageCategoriesViewModel.remove(viewHolder.adapterPosition)
                }
            })

        itemTouchHelper.attachToRecyclerView(binding.existingCategories)

        return binding.root
    }
}