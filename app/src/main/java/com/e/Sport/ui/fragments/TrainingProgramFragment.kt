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
import com.e.Sport.data.Exercise

import com.e.Sport.R
import com.e.Sport.ui.dialogs.AddOrChangeExerciseDialog
import com.e.Sport.ui.dialogs.ListDialog
import com.e.Sport.ui.recyclerviews.ExercisesRecyclerAdapter
import com.e.Sport.ui.recyclerviews.ItemHelper
import com.e.Sport.utils.rxutils.throttleFirstClick
import com.e.Sport.viewmodels.ProgramViewModel
import com.e.Sport.viewmodels.States.ProgramSate
import com.jakewharton.rxbinding.view.RxView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_training_program.*
import kotlinx.android.synthetic.main.fragment_training_program.view.*
import kotlinx.android.synthetic.main.fragment_training_program.view.programNameTrainingProgramFragment
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class TrainingProgramFragment : DaggerFragment() {

    @Inject
    lateinit var provider: ViewModelProvider.Factory

    private lateinit var ctx: Context
    private lateinit var viewModel: ProgramViewModel
    private lateinit var adapter: ExercisesRecyclerAdapter

    private val immutableMap=HashMap<String,Boolean>()
    private var exerciseHmap=HashMap<String, Exercise>()
    private var prevProgramName=""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx=context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_training_program, container, false)
        val saveBtn=view.saveBtnTrainingProgramFragment
        val addBtn=view.addExerciseBtnTrainingProgramFragment
        val programEditText=view.programNameTrainingProgramFragment
        val recyclerView=view.recyclerviewTrainingProgramFragment
        val exerciseList=view.immutableExercisesListTrainingProgramFragment

        viewModel=ViewModelProvider(activity!!,provider)[ProgramViewModel::class.java]

        initRecyclerView(recyclerView)

        saveBtn.setOnClickListener {
            val newProgramName=programEditText.text.toString()
            viewModel.saveProgram(exerciseHmap,prevProgramName,newProgramName)
        }

        addBtn.setOnClickListener {
            addOrUpdateExerciseDialog(Exercise())
        }

        RxView.clicks(exerciseList)
        .throttleFirstClick()
        .filter {immutableMap.isEmpty()  }
        .subscribe { viewModel.immutableExerciseList() }

        return view
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager= LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        adapter= ExercisesRecyclerAdapter(exerciseHmap){ item->
            AddOrChangeExerciseDialog().showDialog(ctx,item){
                adapter.notifyDataSetChanged()
            }
        }
        recyclerView.adapter=adapter
        recyclerView.setHasFixedSize(true)
        val itemHelper= ItemHelper(ctx){
            val exercise= exerciseHmap.keys.elementAt(it)
            viewModel.deleteExercise(exercise)
        }
        itemHelper.attachToRecyclerView(recyclerView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getState().observe(viewLifecycleOwner, Observer {state->
            when(state){
                is ProgramSate.AddExercise->addExercise(state.exercise)
                is ProgramSate.GetClickedProgramName->getProgram(state.name,state.program)
                is ProgramSate.DeleteExercise->deleteExercise(state.name)
                is ProgramSate.ShowConstExerciseList->showConstExerciseList(state.list)
            }
        })
    }

    private fun addOrUpdateExerciseDialog(exercise: Exercise){
        AddOrChangeExerciseDialog().showDialog(ctx,exercise){
            viewModel.deleteExercise(exercise.name)
            viewModel.addExercise(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun deleteExercise(name: String) {
        exerciseHmap.remove(name)
        adapter.notifyDataSetChanged()
    }

    private fun addExercise(exercise: Exercise){
        exerciseHmap[exercise.name]= exercise
        adapter.notifyDataSetChanged()
    }

    fun getProgram(name:String,program:HashMap<String,Exercise>){
        exerciseHmap.putAll(program)
        prevProgramName=name
        programNameTrainingProgramFragment.setText(name)
    }
    fun showConstExerciseList(list:LinkedHashMap<String,Boolean>){
        ListDialog(ctx).show(list,exerciseHmap.keys.toHashSet()) { name: String, isChecked: Boolean ->
            when (isChecked) {
            true->viewModel.addExercise(Exercise(name))
            false->viewModel.deleteExercise(name)
        }
        }
    }
}
