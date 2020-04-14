package shander.annelisapp.ui.projectsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import io.reactivex.FlowableSubscriber
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription
import shander.annelisapp.R
import shander.annelisapp.room.db.ProjectsDatabase
import shander.annelisapp.room.entity.ProjectWithAllNested
import shander.annelisapp.room.entity.measurements.Measurement

class MainActivity : AppCompatActivity() {

    var mDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val db: ProjectsDatabase = ProjectsDatabase.getDatabase(this.applicationContext)
        db.isOpen
        Handler().postDelayed({mDisposable.add(db.projectsDao().getAll()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Log.wtf("RESULT", it.toString()) },
                { Log.wtf("ERROR", it.localizedMessage) }))}, 5000)

    }
}
