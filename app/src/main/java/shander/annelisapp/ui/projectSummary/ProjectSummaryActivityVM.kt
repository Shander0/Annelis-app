package shander.annelisapp.ui.projectSummary

import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import shander.annelisapp.room.db.ProjectsDatabase

class ProjectSummaryActivityVM: ViewModel() {
    private lateinit var subscription: CompositeDisposable

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }
}