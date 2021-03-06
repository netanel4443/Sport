package com.e.Sport.usecases

import com.e.Sport.data.Exercise
import com.e.Sport.realmDB.repo.TrainingProgramsDB
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class ExerciseUseCases @Inject constructor(
          private val repo:TrainingProgramsDB) {

    fun getProgramsNames():Observable<HashMap<String, HashMap<String, Exercise>>> {
        return repo.getProgramsNames()
            .filter{it.isNotEmpty()}
    }

    fun saveProgram(program:HashMap<String, Exercise>,  newName:String):Completable{
        return repo.saveGroceryListToDB(program,newName)
    }

    fun deleteProgram(name: String): Completable {
        return repo.deleteFromDB(name)
    }

    fun immutableExerciseList(): Single<LinkedHashMap<String, Boolean>> {
        return repo.immutableExerciseList()
    }

}