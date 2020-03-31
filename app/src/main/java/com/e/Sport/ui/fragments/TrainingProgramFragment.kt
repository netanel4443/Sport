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
import com.e.Sport.ui.recyclerviews.ExercisesRecyclerAdapter
import com.e.Sport.ui.recyclerviews.ItemHelper
import com.e.Sport.viewmodels.ProgramViewModel
import com.e.Sport.viewmodels.States.ProgramSate
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


        viewModel=ViewModelProvider(activity!!,provider)[ProgramViewModel::class.java]

        initRecyclerView(recyclerView)

        saveBtn.setOnClickListener {
            val newProgramName=programEditText.text.toString()
            viewModel.saveProgram(exerciseHmap,prevProgramName,newProgramName)
        }

        addBtn.setOnClickListener {
            addOrUpdateExerciseDialog(Exercise())
           // viewModel.addExercise(Exercise(UUID.randomUUID().toString()))
        }

        return view
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager= LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        adapter= ExercisesRecyclerAdapter(exerciseHmap){ item->
            val reps=item.reps;val sets=item.sets
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

  }
