package shander.annelisapp.ui.projectSummary.fragment.tasks

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
        binding = TasksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TasksViewModel::class.java)
        viewModel.setId(arguments?.getInt("id", -1)!!)
    }

}
