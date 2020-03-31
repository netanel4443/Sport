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
import com.e.Sport.R
import com.e.Sport.ui.dialogs.SimpleTextDialog
import com.e.Sport.ui.recyclerviews.GroceryListsAdapter
import com.e.Sport.data.GroceryItem
import com.e.Sport.ui.dialogs.CirclePrograssDialog
import com.e.Sport.viewmodels.GroceryViewModel
import com.e.Sport.utils.addFragment
import com.e.Sport.utils.replaceAll
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list_of_menus.view.*
import javax.inject.Inject

class ListOfMenusFragment : DaggerFragment() {

    @Inject lateinit var provider:ViewModelProvider.Factory

    private lateinit var viewModel:GroceryViewModel
    private lateinit var ctx:Context
    private lateinit var groceryListsAdapter:GroceryListsAdapter
    private lateinit var progressBar:CirclePrograssDialog

    private var groceryLists = HashMap<String, HashMap<String, GroceryItem>>()
    private var groceryListsName=HashSet<String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx=context
        progressBar= CirclePrograssDialog(ctx)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_list_of_menus, container, false)
        val recyclerView=view.recyclerViewListOfMenusFragment
        val newMenuBtn=view.addMenuListBtnListOfMenusFragment
        viewModel=activity.run { ViewModelProvider(this!!,provider)[GroceryViewModel::class.java] }

        viewModel.getMenus()

        newMenuBtn.setOnClickListener {
            viewModel.setMenuName("New menu")
            fragmentTransaction()
        }

        initRecyclerView(recyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getGroceriesMenus().observe(viewLifecycleOwner, Observer {
            groceryLists=it
            groceryListsName.replaceAll(it.keys)
            groceryListsAdapter.notifyDataSetChanged()
        })
        viewModel.progressBarState().observe(viewLifecycleOwner, Observer {
            when(it){
                "show" ->progressBar.showprogressbar()
                "hide" ->progressBar.dismiss()
            }
        })
    }

    private fun initRecyclerView(recyclerView:RecyclerView){
        groceryListsAdapter= GroceryListsAdapter(groceryListsName){listName,clickType->
            when(clickType){
                GroceryListsAdapter.ClickTypes.click->{
                    viewModel.getGroceryListClicked(listName)
                    fragmentTransaction()
                }
                GroceryListsAdapter.ClickTypes.deleteClick->{
                    SimpleTextDialog().show(ctx,"Delete $listName ?"){
                        viewModel.deleteMenu(listName)
                    }
                }
            }
        }
        recyclerView.layoutManager= LinearLayoutManager(ctx, RecyclerView.VERTICAL,false)
        recyclerView.adapter=groceryListsAdapter
        recyclerView.setHasFixedSize(true)
    }

    private fun fragmentTransaction(){
        activity?.addFragment(GroceriesFragment(),R.id.frame_layout,"GroceriesFragment")
    }
}
