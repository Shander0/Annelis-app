package shander.annelisapp.ui.commonDialogs

import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmationDialog : DialogFragment() {

    private lateinit var listener: ConfirmationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = parentFragment as ConfirmationListener
    }
    interface ConfirmationListener{
        fun confirmed(callerID: Int)
        fun declined(callerID: Int)
    }
}