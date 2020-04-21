package shander.annelisapp.ui.projectSummary.fragment.tasks

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import shander.annelisapp.R
import shander.annelisapp.room.entity.tasks.*
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.THIRD
import java.text.SimpleDateFormat
import java.util.*

class TaskItemVM : ViewModel() {
    val taskDescription = MutableLiveData<String>()
    val taskCreatedDate = MutableLiveData<String>()
    val taskPlannedDate = MutableLiveData<String>()
    val taskEndedDate = MutableLiveData<String>()
    val taskLevel = MutableLiveData<Int>()
    val editingVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val checkingVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val expandableVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val innerAddVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val btnExpanded: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isExpandable = false
    var isExpanded: Boolean = false


    fun bind(item: Any) {
        when (item) {
            is TaskFirstWithNestedList -> {
                taskDescription.value = item.taskFirstLevel!!.firstTaskDescription
                taskCreatedDate.value = convertLongToTime(item.taskFirstLevel!!.firstTaskStartDate)
                taskPlannedDate.value = convertLongToTime(item.taskFirstLevel!!.firstTaskStartDate + item.taskFirstLevel!!.firstTaskPlannedDuration)
                taskEndedDate.value = convertLongToTime(item.taskFirstLevel!!.firstTaskEndedDate)
                isExpandable = item.secondsList.isNotEmpty()
                taskLevel.value = 1
            }
            is TaskSecondWithThirdsList -> {
                taskDescription.value = item.taskSecondLevel!!.secondTaskDescription
                taskCreatedDate.value = convertLongToTime(item.taskSecondLevel!!.secondTaskStartDate)
                taskPlannedDate.value = convertLongToTime(item.taskSecondLevel!!.secondTaskStartDate + item.taskSecondLevel!!.secondTaskPlannedDuration)
                taskEndedDate.value = convertLongToTime(item.taskSecondLevel!!.secondTaskEndedDate)
                isExpandable = item.thirdsList.isNotEmpty()
                taskLevel.value = 2
            }
            is TaskThirdLevel -> {
                taskDescription.value = item.thirdTaskDescription
                taskCreatedDate.value = convertLongToTime(item.thirdTaskStartDate)
                taskPlannedDate.value = convertLongToTime(item.thirdTaskStartDate + item.thirdTaskPlannedDuration)
                taskEndedDate.value = convertLongToTime(item.thirdTaskEndedDate)
                taskLevel.value = 3
                innerAddVisibility.value = View.GONE
            }
        }
    }

    fun notifyExpand(isExpanded: Boolean) {
        btnExpanded.value = isExpanded
        this.isExpanded = isExpanded
    }

    fun setEdit(isEdit: Boolean) {
        editingVisibility.value = if (isEdit) View.VISIBLE else View.GONE
        checkingVisibility.value = if (!isEdit) View.VISIBLE else View.GONE
        innerAddVisibility.value = if (isEdit && taskLevel.value != THIRD) View.VISIBLE else View.GONE
        expandableVisibility.value = if (!isEdit && isExpandable) View.VISIBLE else View.GONE
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        return format.format(date)
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        return df.parse(date).time
    }
}