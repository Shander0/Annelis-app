package shander.annelisapp.ui.projectsList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import shander.annelisapp.room.entity.Project
import shander.annelisapp.room.entity.tasks.TaskFirstLevel

class ProjectsListVM: ViewModel() {

    private val projectPic = MutableLiveData<String>()
    private val projectName = MutableLiveData<String>()
    private val projectCurrentTasks = MutableLiveData<Int>()

    fun bind(project: Project, currentTasks: List<TaskFirstLevel>){
        projectPic.value = project.projectAvatar
        projectName.value = project.projectName
        projectCurrentTasks.value = currentTasks.size
    }

    fun getProjectName(): MutableLiveData<String> {
        return projectName
    }

    fun getTaskCount(): MutableLiveData<Int> {
        return projectCurrentTasks
    }

    fun getProjectImage(): MutableLiveData<String> {
        return projectPic
    }

}