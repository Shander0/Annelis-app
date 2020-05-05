package shander.annelisapp.ui.projectsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import shander.annelisapp.R

class ProjectDeleteDialog : DialogFragment() {

    private lateinit var listener: ProjectDeleteCallback
    private var name: String? = ""

    companion object {

        const val TAG = "project_del_dlg"

        const val KEY_NAME = "param2"


        @JvmStatic
        fun newInstance(name: String) =
            ProjectDeleteDialog().apply {
                arguments = Bundle().apply {
                    putString(KEY_NAME, name)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = activity as ProjectDeleteCallback
        name = arguments?.getString(KEY_NAME)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dlg_task_delete, container, false)
        view.findViewById<TextView>(R.id.task_del_title).setText(R.string.project_del_title)
        view.findViewById<TextView>(R.id.task_del_details).text = name
        val btnAccept = view.findViewById<Button>(R.id.btn_del_accept)
        val btnDecline = view.findViewById<Button>(R.id.btn_del_decline)

        btnAccept.setOnClickListener {
            listener.projectDeleteConfirmed()
            dismiss()
        }
        btnDecline.setOnClickListener{ dismiss() }

        return view
    }

    interface ProjectDeleteCallback {
        fun projectDeleteConfirmed()
    }
}