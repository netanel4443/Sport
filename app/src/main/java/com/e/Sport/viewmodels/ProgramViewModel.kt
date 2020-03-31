package com.e.Sport.viewmodels

import android.media.tv.TvContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.Sport.data.Exercise
import com.e.Sport.usecases.ExerciseUseCases
import com.e.Sport.viewmodels.States.ProgramSate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProgramViewModel @Inject constructor(
    private val useCases:ExerciseUseCases):ViewModel(){

    private var state=MutableLiveData<ProgramSate>()
    private val compositeDisposable=CompositeDisposable()

    fun resetState(){
        state.value=ProgramSate.Idle
    }

    fun getState():LiveData<ProgramSate>{
        return state
    }

    fun addExercise(exercise: Exercise){
        state.value=ProgramSate.AddExercise(exercise)
    }
    fun getNameOfClickedProgram(name: String,program:HashMap<String,Exercise>){
        state.value=ProgramSate.GetClickedProgramName(name,program)
    }

    fun getProgramsNames() {

        compositeDisposable.add(useCases.getProgramsNames()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { it.printStackTrace() }
            .subscribe{
              state.value=ProgramSate.ListOfPrograms(it)
                println("get ${it.size}")
            })
    }

    fun deleteProgram(programName:String){
        compositeDisposable.add(
            useCases.deleteProgram(programName)
                .doOnSubscribe { state.value= ProgramSate.ShowProgress }
                .doOnComplete  { state.value=ProgramSate.DeleteProgram(programName) }
                .doOnTerminate { state.value= ProgramSate.HideProgress }
            .subscribe())

    }

    fun saveProgram(program:HashMap<String, Exercise>, prevName:String, newName:String){
        println("viewmodel $prevName $newName")
        compositeDisposable.add(useCases.deleteProgram(prevName)
            .doOnSubscribe {state.value=ProgramSate.ShowProgress  }
            .concatWith{
             useCases.saveProgram(program,newName)
                 .doOnSubscribe { compositeDisposable.add(it) }
                 .doOnComplete { state.value=ProgramSate.UpdateProgramName(prevName,newName,program) }
                 .doOnTerminate { state.value= ProgramSate.HideProgress  }
                 .subscribe({}, {it.printStackTrace()})
            }
            .subscribe({},{ it.printStackTrace()
                          state.value=  ProgramSate.HideProgress }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun deleteExercise(name: String) {
        state.value=ProgramSate.DeleteExercise(name)
    }
}