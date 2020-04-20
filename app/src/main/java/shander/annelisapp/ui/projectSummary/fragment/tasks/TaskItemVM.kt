package shander.annelisapp.ui.projectSummary.fragment.tasks

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import shander.annelisapp.room.entity.tasks.TaskFirstLevel
import shander.annelisapp.room.entity.tasks.TaskSecondLevel
import shander.annelisapp.room.entity.tasks.TaskThirdLevel
import java.text.SimpleDateFormat
import java.util.*

class TaskItemVM : ViewModel() {
    private val taskDescription = MutableLiveData<String>()
    private val taskCreatedDate = MutableLiveData<String>()
    private val taskPlannedDate = MutableLiveData<String>()
    private val taskEndedDate = MutableLiveData<String>()
    private val taskLevel = MutableLiveData<Int>()
    val editingVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val checkingVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)


    fun bind(item: Any) {
        when (item) {
            is TaskFirstLevel -> {
                taskDescription.value = item.firstTaskDescription
                taskCreatedDate.value = convertLongToTime(item.firstTaskStartDate)
                taskPlannedDate.value = convertLongToTime(item.firstTaskStartDate + item.firstTaskPlannedDuration)
                taskEndedDate.value = convertLongToTime(item.firstTaskEndedDate)
                taskLevel.value = 1
            }
            is TaskSecondLevel -> {
                taskDescription.value = item.secondTaskDescription
                taskCreatedDate.value = convertLongToTime(item.secondTaskStartDate)
                taskPlannedDate.value = convertLongToTime(item.secondTaskStartDate + item.secondTaskPlannedDuration)
                taskEndedDate.value = convertLongToTime(item.secondTaskEndedDate)
                taskLevel.value = 2
            }
            is TaskThirdLevel -> {
                taskDescription.value = item.thirdTaskDescription
                taskCreatedDate.value = convertLongToTime(item.thirdTaskStartDate)
                taskPlannedDate.value = convertLongToTime(item.thirdTaskStartDate + item.thirdTaskPlannedDuration)
                taskEndedDate.value = convertLongToTime(item.thirdTaskEndedDate)
                taskLevel.value = 3
            }
        }
    }

    fun setEdit(isEdit: Boolean) {
        editingVisibility.value = if (isEdit) View.VISIBLE else View.GONE
        checkingVisibility.value = if (!isEdit) View.VISIBLE else View.GONE
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