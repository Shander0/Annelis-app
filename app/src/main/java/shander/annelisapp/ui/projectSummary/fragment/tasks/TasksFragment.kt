package shander.annelisapp.ui.projectSummary.fragment.tasks

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import shander.annelisapp.databinding.TasksFragmentBinding
import shander.annelisapp.room.db.ProjectsDatabase
import shander.annelisapp.room.entity.tasks.TaskFirstLevel
import shander.annelisapp.room.entity.tasks.TaskSecondLevel
import shander.annelisapp.room.entity.tasks.TaskThirdLevel
import shander.annelisapp.ui.commonDialogs.ConfirmationDialog
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.FIRST
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.SECOND
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.THIRD
import shander.annelisapp.ui.projectSummary.fragment.tasks.TasksViewModel.Companion.INNER_IN_FIRST
import shander.annelisapp.ui.projectSummary.fragment.tasks.TasksViewModel.Companion.INNER_IN_SECOND
import shander.annelisapp.ui.projectSummary.fragment.tasks.TasksViewModel.Companion.INSERT
import shander.annelisapp.ui.projectSummary.fragment.tasks.TasksViewModel.Companion.REMOVE

class TasksFragment : Fragment(), TasksViewModel.TaskListener, TaskAddDialog.TaskAddCallback,
    ConfirmationDialog.ConfirmationListener, TaskDeleteDialog.TaskDeleteCallback {

    private lateinit var binding: TasksFragmentBinding
    private var projectId = 0

    companion object {
        fun newInstance(id: Int) = TasksFragment()
            .apply {
            arguments = Bundle().apply {
                putInt("id", id)
            }
        }
    }

    private lateinit var viewModel: TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(TasksViewModel::class.java)
        projectId = arguments?.getInt("id", -1)!!
        viewModel.init(projectId, this)
        binding = TasksFragmentBinding.inflate(inflater, container, false)
        binding.tasksList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.viewModel = viewModel
        binding.tasksList.adapter = viewModel.adapter

        binding.fab.setOnClickListener { viewModel.adapter.setIsEditing(!viewModel.adapter.isEditMode) }

        return binding.root
    }

    override fun taskEvent(type: Int, taskId: Int, level: Int) {
        when (type) {
            INSERT -> {
                val transaction = childFragmentManager.beginTransaction()
                val previous = childFragmentManager.findFragmentByTag(TaskAddDialog.TAG)
                if (previous != null) {
                    transaction.remove(previous)
                }
                transaction.addToBackStack(null)

                val dialogFragment = TaskAddDialog.newInstance(level, taskId)
                dialogFragment.show(transaction, TaskAddDialog.TAG)
            }
            INNER_IN_FIRST -> {
                val transaction = childFragmentManager.beginTransaction()
                val previous = childFragmentManager.findFragmentByTag(TaskAddDialog.TAG)
                if (previous != null) {
                    transaction.remove(previous)
                }
                transaction.addToBackStack(null)

                val dialogFragment = TaskAddDialog.newInstance(SECOND, taskId)
                dialogFragment.show(transaction, TaskAddDialog.TAG)
            }
            INNER_IN_SECOND -> {
                val transaction = childFragmentManager.beginTransaction()
                val previous = childFragmentManager.findFragmentByTag(TaskAddDialog.TAG)
                if (previous != null) {
                    transaction.remove(previous)
                }
                transaction.addToBackStack(null)

                val dialogFragment = TaskAddDialog.newInstance(THIRD, taskId)
                dialogFragment.show(transaction, TaskAddDialog.TAG)
            }
            REMOVE -> {
                val transaction = childFragmentManager.beginTransaction()
                val previous = childFragmentManager.findFragmentByTag(TaskDeleteDialog.TAG)
                if (previous != null) {
                    transaction.remove(previous)
                }
                transaction.addToBackStack(null)

                val dialogFragment = TaskDeleteDialog.newInstance(level, taskId)
                dialogFragment.show(transaction, TaskDeleteDialog.TAG)
            }
        }
    }

    override fun addConfirmed(level: Int, parentID: Int, name: String) {
        viewModel.dlgAddConfirmed(level, parentID, name)
    }

    override fun deleteConfirmed(level: Int, id: Int) {
        viewModel.dlgDeleteConfirmed(level, id)
    }

    override fun confirmed(callerID: Int) {

    }

    override fun declined(callerID: Int) {
        TODO("Not yet implemented")
    }

}
