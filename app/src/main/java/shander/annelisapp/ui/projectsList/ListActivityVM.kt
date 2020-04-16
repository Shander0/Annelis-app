package shander.annelisapp.ui.projectsList

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import io.reactivex.subscribers.ResourceSubscriber
import shander.annelisapp.room.db.ProjectsDatabase
import shander.annelisapp.room.entity.ProjectWithAllNested
import shander.annelisapp.utils.ProjectWANToSimpleConverter
import java.util.function.Consumer

class ListActivityVM: ViewModel(), ProjectsAdapter.ProjectClickListener {
    private lateinit var subscription: CompositeDisposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val projectsAdapter = ProjectsAdapter(this)

    init {
        Log.wtf("CALL", "INIT")
        onLoadProjectsListStart()
        val db: ProjectsDatabase = ProjectsDatabase.getDatabase()
        subscription = CompositeDisposable()
        subscription.add(db.projectsDao().getAll()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onLoadProjectsListSuccess(it) }))
    }

    private fun onLoadProjectsListStart(){
        loadingVisibility.value = View.VISIBLE
    }

    private fun onLoadProjectsListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onLoadProjectsListSuccess(projectsList:List<ProjectWithAllNested>){
        Log.wtf("SUCCESS", "CALLED")
        subscription.add(Observable.just(ProjectWANToSimpleConverter(projectsList).getSimple())
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {projectsAdapter.updateProjectsList(it)
                onLoadProjectsListFinish()},
                { Log.wtf("FILLING ERROR", it.localizedMessage)}
            ))
    }

    private fun onLoadProjectsListError(){

    }

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }

    override fun onProjectClicked(id: Int) {
        TODO("Not yet implemented")
    }
}