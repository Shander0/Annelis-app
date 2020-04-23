package shander.annelisapp.ui.projectSummary.fragment.tasks

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import shander.annelisapp.R

class TaskAddDialog : DialogFragment() {

    private lateinit var listener: TaskAddCallback
    private var level: Int? = 0
    private var parent: Int? = 0

    companion object {

        const val TAG = "task_add_dlg"

        const val KEY_LEVEL = "param1"
        const val KEY_PARENT = "param2"


        @JvmStatic
        fun newInstance(level: Int, parent: Int) =
            TaskAddDialog().apply {
                arguments = Bundle().apply {
                    putInt(KEY_LEVEL, level)
                    putInt(KEY_PARENT, parent)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = parentFragment as TaskAddCallback
        level = arguments?.getInt(KEY_LEVEL)
        parent = arguments?.getInt(KEY_PARENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dlg_task_add, container, false)
        val btnAccept = view.findViewById<Button>(R.id.btn_add_accept)
        val btnDecline = view.findViewById<Button>(R.id.btn_add_decline)
        val nameEdit = view.findViewById<EditText>(R.id.task_add_name)

        var nameString = ""

        nameEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nameString = p0.toString()
                btnAccept.isClickable = nameString.isNotEmpty()
            }
        })

        btnAccept.isClickable = false
        btnAccept.setOnClickListener {
            listener.addConfirmed(level!!, parent!!, nameString)
            dismiss()
        }
        btnDecline.setOnClickListener{ dismiss() }

        return view
    }

    interface TaskAddCallback {
        fun addConfirmed(level: Int, parentID: Int, name: String)
    }
}