package shander.annelisapp.ui.projectSummary.fragment.tasks

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import shander.annelisapp.databinding.TasksFragmentBinding

class TasksFragment : Fragment() {

    private lateinit var binding: TasksFragmentBinding

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
        viewModel.setId(arguments?.getInt("id", -1)!!)
        binding = TasksFragmentBinding.inflate(inflater, container, false)
        binding.tasksList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.viewModel = viewModel
        binding.tasksList.adapter = viewModel.adapter

        binding.fab.setOnClickListener { viewModel.adapter.setIsEditing(!viewModel.adapter.isEditMode) }

        return binding.root
    }

}
