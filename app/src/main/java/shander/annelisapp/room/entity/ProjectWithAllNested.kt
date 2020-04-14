package shander.annelisapp.room.entity

import androidx.room.Embedded
import androidx.room.Relation
import shander.annelisapp.room.entity.measurements.ListMeasures
import shander.annelisapp.room.entity.measurements.ListWithMeasurements
import shander.annelisapp.room.entity.tasks.TaskFirstLevel
import shander.annelisapp.room.entity.tasks.TaskFirstWithNestedList

data class ProjectWithAllNested(
    @Embedded
    var project: Project? = null,

    @Relation(parentColumn = "projectId",
        entityColumn = "parentProjectId",
        entity = TaskFirstLevel::class)
    var tasks: List<TaskFirstWithNestedList> = ArrayList(),

    @Relation(parentColumn = "projectId",
        entityColumn = "parentProjectId",
        entity = ListMeasures::class)
    var measures: ListWithMeasurements,

    @Relation(parentColumn = "projectId",
        entityColumn = "parentProjectId",
        entity = Material::class)
    var materials: List<Material> = ArrayList(),

    @Relation(parentColumn = "projectId",
        entityColumn = "parentProjectId",
        entity = Photo::class)
    var photos: List<Photo> = ArrayList()
)
