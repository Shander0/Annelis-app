package shander.annelisapp.rx

import android.content.Context
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import shander.annelisapp.room.db.ProjectsDatabase
import shander.annelisapp.room.entity.ProjectWithAllNested

class BaseHelper (context: Context) {

    private val db: ProjectsDatabase = ProjectsDatabase.getDatabase(context.applicationContext)
    private val mContext: Context = context

}