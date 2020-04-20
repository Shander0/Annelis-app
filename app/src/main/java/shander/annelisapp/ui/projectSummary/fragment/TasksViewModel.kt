package shander.annelisapp.ui.projectSummary.fragment

import android.util.Log
import androidx.lifecycle.ViewModel

class TasksViewModel : ViewModel() {
    private var projectId: Int = -1

    fun setId(id: Int) {
        projectId = id
    }
}
