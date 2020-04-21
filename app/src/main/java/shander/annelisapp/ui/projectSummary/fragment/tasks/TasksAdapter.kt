package shander.annelisapp.ui.projectSummary.fragment.tasks

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import shander.annelisapp.R
import shander.annelisapp.databinding.ItemTaskFirstBinding
import shander.annelisapp.databinding.ItemTaskSecondBinding
import shander.annelisapp.databinding.ItemTaskThirdBinding
import shander.annelisapp.room.entity.tasks.*
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.FIRST
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.SECOND
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.THIRD

class TasksAdapter(val listener: TaskClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var actionLock = false
    var isEditMode = false
    private var taskModelList: MutableList<TaskRowModel> = mutableListOf()

    fun updateTasks(tasks: List<TaskFirstWithNestedList>) {
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
        fun bind(item: TaskFirstWithNestedList, expanded: Boolean){
            viewModel.bind(item)
            binding.viewModel = viewModel
            viewModel.notifyExpand(expanded)
            viewModel.setEdit(isEditMode)
            binding.btnAdd.setOnClickListener{ listener.taskAddClick(item.taskFirstLevel!!.firstTaskId, FIRST) }
            binding.btnDate.setOnClickListener { listener.taskClockClick(item.taskFirstLevel!!.firstTaskId) }
            binding.btnInnerAdd.setOnClickListener { listener.taskInnerAddClick(item.taskFirstLevel!!.firstTaskId) }
            binding.btnRemove.setOnClickListener { listener.taskRemoveClick(item.taskFirstLevel!!.firstTaskId) }
            binding.taskDoneCheck.setOnCheckedChangeListener { _, b -> listener.taskChecked(item.taskFirstLevel!!.firstTaskId, b) }
            binding.btnExpand.setOnClickListener {
                if (!actionLock) {
                    actionLock = true
                    Log.wtf("IN VM", " " + viewModel.isExpanded)
                    if (viewModel.isExpanded) {
                        viewModel.notifyExpand(false)
                        taskModelList[adapterPosition].isExpanded = false
                        collapse(adapterPosition)
                    } else {
                        viewModel.notifyExpand(true)
                        taskModelList[adapterPosition].isExpanded = true
                        expand(adapterPosition)
                    }
                }
            }
        }
    }

    inner class SecondLevelTaskViewHolder(private val binding: ItemTaskSecondBinding):
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = TaskItemVM()
        fun bind(item: TaskSecondWithThirdsList, expanded: Boolean){
            viewModel.bind(item)
            binding.viewModel = viewModel
            viewModel.notifyExpand(expanded)
            viewModel.setEdit(isEditMode)
            binding.btnAdd.setOnClickListener{ listener.taskAddClick(item.taskSecondLevel!!.secondTaskId, SECOND) }
            binding.btnDate.setOnClickListener { listener.taskClockClick(item.taskSecondLevel!!.secondTaskId) }
            binding.btnInnerAdd.setOnClickListener { listener.taskInnerAddClick(item.taskSecondLevel!!.secondTaskId) }
            binding.btnRemove.setOnClickListener { listener.taskRemoveClick(item.taskSecondLevel!!.secondTaskId) }
            binding.taskDoneCheck.setOnCheckedChangeListener { _, b -> listener.taskChecked(item.taskSecondLevel!!.secondTaskId, b) }
            binding.btnExpand.setOnClickListener {
                if (!actionLock) {
                    actionLock = true
                    Log.wtf("IN VM", " " + viewModel.isExpanded)
                    if (viewModel.isExpanded) {
                        viewModel.notifyExpand(false)
                        taskModelList[adapterPosition].isExpanded = false
                        collapse(adapterPosition)
                    } else {
                        viewModel.notifyExpand(true)
                        taskModelList[adapterPosition].isExpanded = true
                        expand(adapterPosition)
                    }
                }
            }
        }
    }

    inner class ThirdLevelTaskViewHolder(private val binding: ItemTaskThirdBinding):
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = TaskItemVM()
        fun bind(item: TaskThirdLevel){
            viewModel.bind(item)
            binding.viewModel = viewModel
            viewModel.setEdit(isEditMode)
            binding.btnAdd.setOnClickListener{ listener.taskAddClick(item.thirdTaskId, FIRST) }
            binding.btnDate.setOnClickListener { listener.taskClockClick(item.thirdTaskId) }
            binding.btnRemove.setOnClickListener { listener.taskRemoveClick(item.thirdTaskId) }
            binding.taskDoneCheck.setOnCheckedChangeListener { _, b -> listener.taskChecked(item.thirdTaskId, b) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return taskModelList[position].level
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FIRST -> FirstLevelTaskViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_task_first, parent, false))
            SECOND -> SecondLevelTaskViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_task_second, parent, false))
            THIRD -> ThirdLevelTaskViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_task_third, parent, false))
            else -> FirstLevelTaskViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_task_first, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return taskModelList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = taskModelList[position]
        when (model.level) {
            FIRST -> (holder as FirstLevelTaskViewHolder).bind(model.taskFirst, model.isExpanded)
            SECOND -> (holder as SecondLevelTaskViewHolder).bind(model.taskSecond, model.isExpanded)
            THIRD -> (holder as ThirdLevelTaskViewHolder).bind(model.taskThird)
        }
    }

    fun expand(position: Int) {

        var nextPosition = position
        val model = taskModelList[position]
        when (model.level) {
            FIRST -> {
                for (task in model.taskFirst.secondsList) {
                    taskModelList.add(++nextPosition, TaskRowModel(SECOND, task))
                }
                notifyDataSetChanged()
            }
            SECOND -> {
                for (task in model.taskSecond.thirdsList) {
                    taskModelList.add(++nextPosition, TaskRowModel(THIRD, task))
                }
                notifyDataSetChanged()
            }
        }

        actionLock = false
    }

    fun collapse(position: Int) {

        var nextPosition = position + 1
        val model = taskModelList[position]
        when (model.level) {
            FIRST -> {
                outerLoop@ while (true) {
                    if (nextPosition == taskModelList.size || taskModelList[nextPosition].level == FIRST) {
                        break@outerLoop
                    }

                    taskModelList.removeAt(nextPosition)
                }
                notifyDataSetChanged()
            }
            SECOND -> {
                outerLoop@ while (true) {
                    if (nextPosition == taskModelList.size ||
                        taskModelList[nextPosition].level == SECOND ||
                        taskModelList[nextPosition].level == FIRST) {
                        break@outerLoop
                    }

                    taskModelList.removeAt(nextPosition)
                }
                notifyDataSetChanged()
            }
        }

        actionLock = false

    }

    interface TaskClickListener {
        fun taskAddClick(taskId: Int, taskLevel: Int)
        fun taskInnerAddClick(taskId: Int)
        fun taskClockClick(taskId: Int)
        fun taskRemoveClick(taskId: Int)
        fun taskChecked(taskId: Int, checked: Boolean)
    }
}