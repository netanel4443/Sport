package com.e.Sport.ui.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.Sport.data.Exercise

import com.e.Sport.R
import com.e.Sport.ui.dialogs.CirclePrograssDialog
import com.e.Sport.ui.dialogs.SimpleTextDialog
import com.e.Sport.ui.recyclerviews.GroceryListsAdapter
import com.e.Sport.utils.addFragment
import com.e.Sport.utils.replace
import com.e.Sport.utils.replaceAll
import com.e.Sport.viewmodels.ProgramViewModel
import com.e.Sport.viewmodels.States.ProgramSate
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ListOfProgramsFragment : DaggerFragment() {

    private lateinit var viewModel:ProgramViewModel
    private lateinit var ctx:Context
    private  lateinit var adapter: GroceryListsAdapter
    private lateinit var progressBar:CirclePrograssDialog
    private var  programsNames=HashSet<String>()
    private var  programsList = HashMap<String, HashMap<String, Exercise>>()

    @Inject lateinit var provider :ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx=context
        progressBar= CirclePrograssDialog(ctx)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_list_of_programs, container, false)
        val addBtn=view.findViewById(R.id.addProgramBtnListOfProgramsFragment) as Button
        val recyclerView=view.findViewById(R.id.recyclerViewListOfProgramsFragment) as RecyclerView

        initRecyclerView(recyclerView)

        viewModel=ViewModelProvider(activity!!,provider)[ProgramViewModel::class.java]

        viewModel.getProgramsNames()

        addBtn.setOnClickListener {
            viewModel.resetState()
            fragmentTransaction()
        }

        return view
    }

    private fun fragmentTransaction() {
        activity?.addFragment(TrainingProgramFragment(),R.id.frame_layout,"TrainingProgramFragment")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getState().observe(viewLifecycleOwner, Observer {state->
            when(state){
                is ProgramSate.ListOfPrograms->updateProgramsList(state.programs)
                is ProgramSate.UpdateProgramName->updateListName(state.prevName,state.newName,state.program)
                is ProgramSate.DeleteProgram->deleteProgram(state.name)
                is ProgramSate.ShowProgress->progressBar.showprogressbar()
                is ProgramSate.HideProgress->progressBar.dismiss()
                //   is ProgramSate.OnProgramClicked->
            }
        })
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        adapter= GroceryListsAdapter(programsNames) { name: String, clickType: GroceryListsAdapter.ClickTypes ->
            when (clickType) {
                GroceryListsAdapter.ClickTypes.click -> {
                       viewModel.getNameOfClickedProgram(name,programsList[name]!!)
                       fragmentTransaction()
                }
                GroceryListsAdapter.ClickTypes.deleteClick -> {
                         SimpleTextDialog().show(ctx,"Delete $name ?"){
                            viewModel.deleteProgram(name)
                         }
                }
            }
        }
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(ctx,RecyclerView.VERTICAL,false)
        recyclerView.setHasFixedSize(true)
    }

    private fun updateProgramsList(list:HashMap<String,HashMap<String, Exercise>>){
        programsList=list
        programsNames.replaceAll(list.keys)
        adapter.notifyDataSetChanged()
        list.forEach{
            println("key ${it.key}")
        }
        println(list.size.toString() +  "halva")
    }

    private fun updateListName(prevName:String,newName:String,program:HashMap<String, Exercise>){
        println("update name $prevName $newName")
        programsList.remove(prevName)
        programsList[newName]=program
        programsNames.replace(prevName,newName)
        adapter.notifyDataSetChanged()
    }

    private fun deleteProgram(name: String) {
        programsList.remove(name)
        programsNames.remove(name)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetState()
    }
}
