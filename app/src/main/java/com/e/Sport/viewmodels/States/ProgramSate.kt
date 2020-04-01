package com.e.Sport.viewmodels.States

import com.e.Sport.data.Exercise
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.HashMap

sealed class ProgramSate {

    data class ListOfPrograms(val programs:HashMap<String,HashMap<String, Exercise>>): ProgramSate()
    data class AddExercise(val exercise: Exercise): ProgramSate()
    data class GetClickedProgramName(val name:String,val program:HashMap<String,Exercise>): ProgramSate()
    data class DeleteExercise(val name: String): ProgramSate()
    data class UpdateProgramName(val prevName:String,val newName:String,val program:HashMap<String,Exercise>):ProgramSate()
    data class DeleteProgram(val name:String): ProgramSate()
    data class ShowConstExerciseList(val list: LinkedHashMap<String, Boolean>) : ProgramSate()
    object ShowProgress: ProgramSate()
    object HideProgress: ProgramSate()
    object Idle:ProgramSate()
}