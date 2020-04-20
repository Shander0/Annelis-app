package shander.annelisapp.ui.projectSummary.fragment.tasks

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task_first.view.*
import shander.annelisapp.R
import shander.annelisapp.databinding.ItemTaskFirstBinding
import shander.annelisapp.databinding.ItemTaskSecondBinding
import shander.annelisapp.databinding.ItemTaskThirdBinding
import shander.annelisapp.room.entity.tasks.TaskFirstLevel
import shander.annelisapp.room.entity.tasks.TaskFirstWithNestedList
import shander.annelisapp.room.entity.tasks.TaskSecondLevel
import shander.annelisapp.room.entity.tasks.TaskThirdLevel
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.FIRST
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.SECOND

class TasksAdapter(val context: Context, val listener: TaskClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var actionLock = false
    private var isEditMode = false
    private var taskModelList: MutableList<TaskRowModel> = mutableListOf()
    private var pureData: List<TaskFirstWithNestedList> = mutableListOf()

    fun updateTasks(tasks: List<TaskFirstWithNestedList>) {
        pureData = tasks
        tasks.forEach { firstsList ->
            run {
                taskModelList.add(TaskRowModel(FIRST, firstsList))
            }
        }
        notifyDataSetChanged()
    }

    fun setIsEditing(edit: Boolean) {
        isEditMode = edit
        notifyDataSetChanged()
    }

    inner class FirstLevelTaskViewHolder(private val binding: ItemTaskFirstBinding):
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = TaskItemVM()
        fun bind(item: TaskFirstLevel){
            viewModel.bind(item)
            binding.viewModel = viewModel
            binding.btnAdd.setOnClickListener{ listener.taskAddClick(item.firstTaskId, FIRST) }
            binding.btnDate.setOnClickListener { listener.taskClockClick(item.firstTaskId) }
            binding.btnInnerAdd.setOnClickListener { listener.taskInnerAddClick(item.firstTaskId) }
            binding.btnRemove.setOnClickListener { listener.taskRemoveClick(item.firstTaskId) }
            binding.taskDoneCheck.setOnCheckedChangeListener { _, b -> listener.taskCheched(item.firstTaskId, b) }
        }
    }

    inner class SecondLevelTaskViewHolder(private val binding: ItemTaskSecondBinding):
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = TaskItemVM()
        fun bind(item: TaskSecondLevel){
            viewModel.bind(item)
            binding.viewModel = viewModel
            binding.btnAdd.setOnClickListener{ listener.taskAddClick(item.secondTaskId, SECOND) }
            binding.btnDate.setOnClickListener { listener.taskClockClick(item.secondTaskId) }
            binding.btnInnerAdd.setOnClickListener { listener.taskInnerAddClick(item.secondTaskId) }
            binding.btnRemove.setOnClickListener { listener.taskRemoveClick(item.secondTaskId) }
            binding.taskDoneCheck.setOnCheckedChangeListener { _, b -> listener.taskCheched(item.secondTaskId, b) }
        }
    }

    inner class ThirdLevelTaskViewHolder(private val binding: ItemTaskThirdBinding):
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = TaskItemVM()
        fun bind(item: TaskThirdLevel){
            viewModel.bind(item)
            binding.viewModel = viewModel
            binding.btnAdd.setOnClickListener{ listener.taskAddClick(item.thirdTaskId, FIRST) }
            binding.btnDate.setOnClickListener { listener.taskClockClick(item.thirdTaskId) }
            binding.btnRemove.setOnClickListener { listener.taskRemoveClick(item.thirdTaskId) }
            binding.taskDoneCheck.setOnCheckedChangeListener { _, b -> listener.taskCheched(item.thirdTaskId, b) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return taskModelList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    interface TaskClickListener {
        fun taskAddClick(taskId: Int, taskLevel: Int)
        fun taskInnerAddClick(taskId: Int)
        fun taskClockClick(taskId: Int)
        fun taskRemoveClick(taskId: Int)
        fun taskCheched(taskId: Int, checked: Boolean)
    }
}