package shander.annelisapp.ui.projectSummary.fragment.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import shander.annelisapp.R

class TaskDeleteDialog : DialogFragment() {

    private lateinit var listener: TaskDeleteCallback
    private var level: Int? = 0
    private var id: Int? = 0
    private var name: String? = ""

    companion object {

        const val TAG = "task_del_dlg"

        const val KEY_LEVEL = "param1"
        const val KEY_ID = "param2"
        const val KEY_NAME = "param3"


        @JvmStatic
        fun newInstance(level: Int, id: Int) =
            TaskDeleteDialog().apply {
                arguments = Bundle().apply {
                    putInt(KEY_LEVEL, level)
                    putInt(KEY_ID, id)
//                    putString(KEY_NAME, name)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = parentFragment as TaskDeleteCallback
        level = arguments?.getInt(KEY_LEVEL)
        id = arguments?.getInt(KEY_ID)
//        name = arguments?.getString(KEY_NAME)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dlg_task_delete, container, false)
        val btnAccept = view.findViewById<Button>(R.id.btn_del_accept)
        val btnDecline = view.findViewById<Button>(R.id.btn_del_decline)
//        val nameText = view.findViewById<TextView>(R.id.task_del_details)
//        nameText.text = name

        btnAccept.setOnClickListener {
            listener.deleteConfirmed(level!!, id!!)
            dismiss()
        }
        btnDecline.setOnClickListener{ dismiss() }

        return view
    }

    interface TaskDeleteCallback {
        fun deleteConfirmed(level: Int, id: Int)
    }
}