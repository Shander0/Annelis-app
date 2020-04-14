package shander.annelisapp.ui.projectsList

import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import shander.annelisapp.room.db.ProjectsDatabase

class StartListActivityVM: ViewModel() {
    private lateinit var subscription: CompositeDisposable

    fun init() {
        val db: ProjectsDatabase = ProjectsDatabase.getDatabase()
        db.isOpen
        Handler().postDelayed({subscription.add(db.projectsDao().getAll()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Log.wtf("RESULT", it.toString()) },
                { Log.wtf("ERROR", it.localizedMessage) }))}, 5000)
    }

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }
}