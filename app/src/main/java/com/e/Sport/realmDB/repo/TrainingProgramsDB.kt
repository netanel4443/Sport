package com.e.Sport.realmDB.repo

import android.util.Log
import com.e.Sport.data.Exercise
import com.e.Sport.realmDB.configurations.TrainingProgramsRealmConfig
import com.e.Sport.realmDB.realmObjects.ExercisesRealmObject
import com.e.Sport.realmDB.realmObjects.ListOfTrainingProgramsRealmObjects
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import javax.inject.Inject

class TrainingProgramsDB @Inject constructor(
      private val realmConfig: TrainingProgramsRealmConfig) {

    fun getProgramsNames(): Observable<HashMap<String, HashMap<String, Exercise>>> {
        return Observable.create { emitter ->
            val list = HashMap<String, HashMap<String, Exercise>>()
            val realm = Realm.getInstance(realmConfig.config())
            try {
                realm.executeTransaction {
                    val programs = realm.where(ListOfTrainingProgramsRealmObjects::class.java)
                        .findAll()

                    programs?.let {

                        it.forEach {
                            val tmpHmap = HashMap<String, Exercise>()

                            it.program.forEach {
                                val exercise = Exercise()

                                exercise.name = it.exerciseName
                                exercise.reps = it.reps
                                exercise.sets = it.sets
                                tmpHmap[exercise.name] = exercise
                                println("exercisename ${exercise.name}")
                            }
                            list[it.programName] = tmpHmap
                            println("sizeee ${list.size}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("RealmError", e.printStackTrace().toString())
            } finally {
                realm?.close()
                 emitter.onNext(list)
                emitter.onComplete()
            }
        }
    }

        fun deleteFromDB(name: String):Completable {
            println("deleting $name")
          return  Completable.create {emitter->
                    val realm = Realm.getInstance(realmConfig.config())
                    try {
                        realm.executeTransaction {
                            val programsList =
                                it.where(ListOfTrainingProgramsRealmObjects::class.java)
                                    .equalTo("programName", name)
                                    .findFirst()
                            programsList?.deleteFromRealm()
                        }
                        realm?.close()
                        emitter.onComplete()
                    } catch (e: Exception) {
                        emitter.onError(e)
                    }
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
             // .subscribe()
        }

    fun saveGroceryListToDB(list: HashMap<String, Exercise>, newProgramName: String):Completable {

      return Completable.create {emitter->
            val realm = Realm.getInstance(realmConfig.config())

            try {
                 realm.executeTransaction {

                  val programsList = realm.createObject(
                        ListOfTrainingProgramsRealmObjects::class.java, newProgramName)

                            list.values.forEach {
                                val exercise = realm.createObject(ExercisesRealmObject::class.java)

                                exercise.exerciseName = it.name
                                exercise.reps = it.reps
                                exercise.sets = it.sets

                                programsList.program.add(exercise)
                            }
                                println("saving $newProgramName")
                                realm.insertOrUpdate(programsList!!)
                        }
                realm?.close()
                emitter.onComplete()
            }
            catch (e: Exception) {
                    realm?.close()
                    emitter.onError(e)
            }
       }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
     //   .subscribe()
    }
}