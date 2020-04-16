package shander.annelisapp.utils

import shander.annelisapp.innerEntity.SimpleProjectItem
import shander.annelisapp.room.entity.ProjectWithAllNested
import java.util.concurrent.Callable

class ProjectWANToSimpleConverter(projectsWithAllNested: List<ProjectWithAllNested>) {

    var projectOrigin = projectsWithAllNested

    fun getSimple(): List<SimpleProjectItem> {
        val simpleList = mutableListOf<SimpleProjectItem>()
        projectOrigin.forEach { proj ->
            val simple = SimpleProjectItem(proj.project!!.projectName,
                proj.project!!.projectAvatar, proj.project!!.projectId, 0)
            proj.tasks.forEach { if (it.taskFirstLevel.firstTaskPlannedDuration < System.currentTimeMillis() &&
                    it.taskFirstLevel.firstTaskEndedDate == 0L) simple.tasks++ }
            simpleList.add(simple)
        }

        return simpleList
    }

}