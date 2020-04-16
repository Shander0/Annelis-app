package shander.annelisapp.ui.projectsList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import shander.annelisapp.innerEntity.SimpleProjectItem
import shander.annelisapp.room.entity.Project
import shander.annelisapp.room.entity.tasks.TaskFirstLevel

class ProjectsItemVM: ViewModel() {

    private val projectPic = MutableLiveData<String>()
    private val projectName = MutableLiveData<String>()
    private val projectCurrentTasks = MutableLiveData<String>()

    fun bind(item: SimpleProjectItem){
        projectPic.value = item.projectIcon
        projectName.value = item.projectName
        projectCurrentTasks.value = item.tasks.toString()
    }

    fun getProjectName(): MutableLiveData<String> {
        return projectName
    }

    fun getTaskCount(): MutableLiveData<String> {
        return projectCurrentTasks
    }

    fun getProjectImage(): MutableLiveData<String> {
        return projectPic
    }

}