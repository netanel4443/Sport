package com.e.Sport.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.Sport.MainActivity
import com.e.Sport.R
import com.e.Sport.ui.dialogs.AddItemDialog
import com.e.Sport.data.GroceryItem
import com.e.Sport.ui.recyclerviews.GroceryRecyclerAdapter
import com.e.Sport.ui.recyclerviews.ItemHelper
import com.e.Sport.viewmodels.GroceryViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.grocery_fragment.*
import kotlinx.android.synthetic.main.grocery_fragment.view.*
import javax.inject.Inject

class
GroceriesFragment : DaggerFragment() {

    @Inject lateinit var provider: ViewModelProvider.Factory

    private lateinit var ctx: Context
    private lateinit var viewModel: GroceryViewModel
    private lateinit var groceryAdapter: GroceryRecyclerAdapter

    private var groceryList=HashMap<String, GroceryItem>()
    private var prevMenuName=""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx=context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.grocery_fragment, container, false)
        val recylerViewGroceryItems=view.recyclerviewGroceryFragment
        val addItem=view.addGroceryItemBtnGroceryFragment
        val save=view.saveTextViewGroceryFragment
        val menuName=view.menuNameGroceryFragment


        viewModel = ViewModelProvider(requireActivity(),provider).get(GroceryViewModel::class.java)

        initRecylerViewGroceryItems(recylerViewGroceryItems)

        addItem.setOnClickListener { addNewItem() }

        save.setOnClickListener {
            val newMenuName=menuName.text.toString()
            viewModel.saveMenu(groceryList,prevMenuName,newMenuName)
        }

        return view
    }

    private fun addNewItem() = AddItemDialog().showDialog(ctx,
        GroceryItem()
    ){
            if (it.name.isNotBlank()) viewModel.addItem(it,groceryList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getGroceryList().observe(viewLifecycleOwner, Observer {
            groceryList.putAll(it)
            groceryAdapter.notifyDataSetChanged()
        })

        viewModel.totalPrice().observe(viewLifecycleOwner, Observer {
            totalPriceTviewGroceryFragment.text = "${it.first} - ${it.second}"
            groceryAdapter.notifyDataSetChanged()
            groceryList.keys.forEach {
                println("grocerylist  $it")
            }
        })

        viewModel.getMenuName().observe(viewLifecycleOwner, Observer {
            prevMenuName=it
            menuNameGroceryFragment.setText(it)
        })
    }

    private fun initRecylerViewGroceryItems(recylerView: RecyclerView) {
        recylerView.layoutManager=LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        groceryAdapter= GroceryRecyclerAdapter(groceryList){item->
            val minPrice=item.minPrice;val maxPrice=item.maxPrice
            AddItemDialog().showDialog(ctx,item){
                groceryAdapter.notifyDataSetChanged()
                viewModel.updateTotalPriceByItemUpdate(minPrice, maxPrice,it.minPrice,it.maxPrice)
            }
        }
        recylerView.adapter=groceryAdapter
        recylerView.setHasFixedSize(true)
        val itemHelper=ItemHelper(ctx){
           val groceryItem= groceryList.keys.elementAt(it)
               viewModel.deleteGroceryItem(groceryItem,groceryList)
        }
        itemHelper.attachToRecyclerView(recylerView)
    }
}
