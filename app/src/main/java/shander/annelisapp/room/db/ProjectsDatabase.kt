package shander.annelisapp.room.db

import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.CompletableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import shander.annelisapp.App
import shander.annelisapp.innerEntity.DefListSample
import shander.annelisapp.innerEntity.DefMeasureSample
import shander.annelisapp.room.dao.*
import shander.annelisapp.room.entity.Material
import shander.annelisapp.room.entity.Photo
import shander.annelisapp.room.entity.Project
import shander.annelisapp.room.entity.defaultMeasures.DefaultListMeasures
import shander.annelisapp.room.entity.defaultMeasures.DefaultMeasurement
import shander.annelisapp.room.entity.measurements.ListMeasures
import shander.annelisapp.room.entity.measurements.Measurement
import shander.annelisapp.room.entity.tasks.TaskFirstLevel
import shander.annelisapp.room.entity.tasks.TaskSecondLevel
import shander.annelisapp.room.entity.tasks.TaskThirdLevel
import shander.annelisapp.utils.getJsonDataFromAsset

@Database(
    entities = [Project::class, Photo::class, Material::class, ListMeasures::class,
        Measurement::class, TaskFirstLevel::class, TaskSecondLevel::class, TaskThirdLevel::class,
        DefaultListMeasures::class, DefaultMeasurement::class], version = 2, exportSchema = true
)
public abstract class ProjectsDatabase : RoomDatabase() {

    abstract fun projectsDao(): ProjectsDao
    abstract fun measurementDao(): MeasurementDao
    abstract fun photoDao(): PhotoDao
    abstract fun listMeasuresDao(): ListMeasuresDao
    abstract fun materialDao(): MaterialDao
    abstract fun taskFirstDao(): TaskFirstLevelDao
    abstract fun taskSecondDao(): TaskSecondLevelDao
    abstract fun taskThirdDao(): TaskThirdLevelDao
    abstract fun defaultMeasureDao(): DefaultMeasureDao
    abstract fun defaultMeasuresListDao(): DefaultMeasuresListDao

    private class ProjectsDatabaseCallback : RoomDatabase.Callback() {

        fun fillMeasures(storedLists: MutableMap<String, DefaultListMeasures>) {
            val jsonFileMeasuresString = getJsonDataFromAsset(App.instance, "measures.json")
            val gson = Gson()
            val measuresLists = object : TypeToken<List<DefMeasureSample>>() {}.type
            val measures: List<DefMeasureSample> =
                gson.fromJson(jsonFileMeasuresString, measuresLists)
            val preparedMeasures: MutableList<DefaultMeasurement> = mutableListOf()
            measures.forEach {
                preparedMeasures.add(
                    DefaultMeasurement(
                        storedLists[it.measuresListId]!!.defListId,
                        it.measureName, it.measureDescription, "", 0.0, ""
                    )
                )
            }

            INSTANCE!!.defaultMeasureDao()
                .insertMany(preparedMeasures)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        Log.wtf("MEASURES FILL", "completed")
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        Log.wtf("Prefill", "Error: " + e.localizedMessage)
                    }
                })
        }

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val jsonFileListString = getJsonDataFromAsset(App.instance, "list.json")
            val gson = Gson()
            val listLists = object : TypeToken<List<DefListSample>>() {}.type
            val lists: List<DefListSample> = gson.fromJson(jsonFileListString, listLists)
            val preparedLists: MutableList<DefaultListMeasures> = mutableListOf()
            lists.forEach { list ->
                preparedLists.add(
                    DefaultListMeasures(
                        list.name,
                        list.description
                    )
                )
            }
            var storedLists: MutableMap<String, DefaultListMeasures> = mutableMapOf()
            var listInsertDisposable: Disposable? = null
            var listQueryDisposable: Disposable? = null
            INSTANCE!!.defaultMeasuresListDao().insertMany(preparedLists)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        listQueryDisposable = INSTANCE!!.defaultMeasuresListDao().getAll().subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe {it.forEach { list ->
                                storedLists[list.defaultListMeasures!!.defListName] = list.defaultListMeasures!!
                            }
                                fillMeasures(storedLists)
                                listQueryDisposable?.dispose()}
                        listInsertDisposable?.dispose()
                    }

                    override fun onSubscribe(d: Disposable) {
                        listInsertDisposable = d
                    }

                    override fun onError(e: Throwable) {
                        Log.wtf("Prefill", "Error: " + e.localizedMessage)
                    }
                })
            INSTANCE!!.projectsDao().insert(
                Project("ТЕСТ 1 пример", "", "", 0)
            ).concatWith(INSTANCE!!.projectsDao().insert(Project("ТЕСТ 2 пример", "", "", 0)))
                .concatWith(INSTANCE!!.projectsDao().insert(Project("ТЕСТ 44 пример", "", "", 0)))
                .concatWith(INSTANCE!!.projectsDao().insert(Project("ТЕСТ 555 пример", "", "", 0)))
                .concatWith(INSTANCE!!.projectsDao().insert(Project("ТЕСТ 6666 пример", "", "", 0)))
                .concatWith(
                    INSTANCE!!.projectsDao().insert(Project("ТЕСТ 77788 пример", "", "", 0))
                )
                .concatWith(INSTANCE!!.projectsDao().insert(Project("ТЕСТ 9 пример", "", "", 0)))
                .concatWith(INSTANCE!!.projectsDao().insert(Project("ТЕСТ 10 пример", "", "", 0)))
                .concatWith(INSTANCE!!.projectsDao().insert(Project("ТЕСТ 11 пример", "", "", 0)))
                .concatWith(INSTANCE!!.projectsDao().insert(Project("ТЕСТ 12 пример", "", "", 0)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        Log.wtf("Prefill", "completed")
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        Log.wtf("Prefill", "Error: " + e.localizedMessage)
                    }
                })

        }
    }

    companion object {

        @Volatile
        private var INSTANCE: ProjectsDatabase? = null

        fun getDatabase(): ProjectsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    App.instance,
                    ProjectsDatabase::class.java,
                    "projects_database"
                ).addCallback(ProjectsDatabaseCallback()).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}